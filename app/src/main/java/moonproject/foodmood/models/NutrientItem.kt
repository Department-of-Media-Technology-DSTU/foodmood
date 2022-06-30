package moonproject.foodmood.models

import moonproject.foodmood.models.realm.FoodInfo

data class NutrientItem(
    val title: String,
    val data: Float,
    val currency: String,
) {

    companion object {

        const val BASE_MASS = 100f

        fun parseFromFoodInfo(foodInfo: FoodInfo): List<NutrientItem> {
            val out = arrayListOf<NutrientItem>()

            out.add(NutrientItem("Белки", foodInfo.proteins, "грамм"))
            out.add(NutrientItem("Жири", foodInfo.fats, "грамм"))
            out.add(NutrientItem("Углеводы", foodInfo.carbs, "грамм"))
            out.add(NutrientItem("Каллории", foodInfo.calories, "ккал"))
            out.add(NutrientItem("Вода", foodInfo.water, "грамм"))

            return out
        }

        fun parseNutrientMass(data: Float, mass: Float) = data * mass / BASE_MASS

    }
}