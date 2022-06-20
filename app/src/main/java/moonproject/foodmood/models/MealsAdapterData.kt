package moonproject.foodmood.models

sealed class MealsAdapterData(
    val type: Int
) {

    data class Header(val title: String, val timeFormatted: String,) : MealsAdapterData(TYPE_HEADER)
    data class Food(val food: String) : MealsAdapterData(TYPE_FOOD)
    class AddFood : MealsAdapterData(TYPE_ADD_FOOD)
    class AddMeal : MealsAdapterData(TYPE_ADD_MEAL)

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FOOD = 1
        const val TYPE_ADD_FOOD = 2
        const val TYPE_ADD_MEAL = 3
    }

}