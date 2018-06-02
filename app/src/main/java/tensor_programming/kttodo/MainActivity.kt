package tensor_programming.kttodo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import tensor_programming.kttodo.model.AddTodo
import tensor_programming.kttodo.model.RemoveTodo
import tensor_programming.kttodo.model.TodoModel
import tensor_programming.kttodo.store.Renderer
import tensor_programming.kttodo.store.TodoStore

class MainActivity : AppCompatActivity(), Renderer<TodoModel> {
    private lateinit var store: TodoStore


    override fun render(model: LiveData<TodoModel>) {
        model.observe(this, Observer { newState ->
            listView.adapter = TodoAdapter(this, newState?.todos ?: listOf())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store = ViewModelProviders.of(this).get(TodoStore::class.java)
        store.subscribe(this)

        addButton.setOnClickListener {
            store.dispatch(AddTodo(editText.text.toString()))
            editText.text = null
        }

        listView.adapter = TodoAdapter(this, listOf())


        listView.setOnItemLongClickListener { _, _, _, id ->
            store.dispatch(RemoveTodo(id))
            true
        }
    }

}
