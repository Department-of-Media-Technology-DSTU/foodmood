package moonproject.foodmood.models

data class AddNewFoodParams(
    val daysOffset: Int,
    val mealIndex: Int,
    var foodInfoId: Long = 0,
    var mass: Float = 0f,
    var shouldUpdate: Boolean = false
)
