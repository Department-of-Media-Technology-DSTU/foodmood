package moonproject.foodmood.adapters

interface MealsClickActions {
    fun addFood(mealIndex: Int)
    fun addMeal(mealName: String, mealsAdapter: MealsAdapter)
}