package tensor_programming.kttodo.store

import android.arch.core.util.Function
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import tensor_programming.kttodo.model.*

/**

 */
class TodoStore : Store<TodoModel>, ViewModel() {
    private val state: MutableLiveData<TodoModel> = MutableLiveData()

    private val initState = TodoModel(listOf())


    override fun dispatch(action: Action) {
        state.value = reduce(state.value, action)
    }

    private fun reduce(state: TodoModel?, action: Action): TodoModel {
        val newState = state ?: initState

        return when(action) {
            is AddTodo -> newState.copy(
                    todos = newState.todos.toMutableList().apply {
                        add(Todo(action.text, action.id))
                    }
            )

            is RemoveTodo -> newState.copy(
                    todos = newState.todos.filter {
                        it.id != action.id
                    } as MutableList<Todo>

            )
        }
    }

    override fun subscribe(renderer: Renderer<TodoModel>, func: Function<TodoModel, TodoModel>) {
        renderer.render(Transformations.map(state, func))
    }


}