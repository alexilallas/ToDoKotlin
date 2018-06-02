package tensor_programming.kttodo.model

/**
 * Created by Tensor on 2/4/2018.
 */
sealed class Action

var counter = 0L
data class AddTodo(val text: String, val id : Long = counter++) : Action()
data class RemoveTodo(val id: Long) : Action()


