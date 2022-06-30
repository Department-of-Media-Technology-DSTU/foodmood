package moonproject.foodmood.storages

import android.util.Log
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import moonproject.foodmood.models.MealsAdapterData
import moonproject.foodmood.models.NutrientItem
import moonproject.foodmood.models.realm.Day
import moonproject.foodmood.models.realm.Food
import moonproject.foodmood.models.realm.FoodInfo
import moonproject.foodmood.models.realm.Meal
import java.util.*

class MealsStorage(private val realm: Realm) {


    fun getFoodInfo(foodInfoId: Long): FoodInfo? {
        return realm.where<FoodInfo>()
            .equalTo(FoodInfo::id.name, foodInfoId).
            findFirst()
    }

    fun saveFoodInfoToRealm(foodInfo: FoodInfo) {
        realm.executeTransaction {
            it.copyToRealmOrUpdate(foodInfo)
        }
    }

    fun addFood(foodInfoId: Long, foodMass: Float, mealIndex: Int, daysOffset: Int) {
        val day = getDay(daysOffset) ?: throw NullPointerException("Cannot add Food to non-existent Day")
        val foodList = day.meals[mealIndex]!!.food

        val foodInfo = realm.where<FoodInfo>()
            .equalTo(FoodInfo::id.name, foodInfoId)
            .findFirst()

        if (day.meals.size <= mealIndex) {
            throw IndexOutOfBoundsException("Cannot add Food to non-existent Meal")
        }

        if (foodInfo == null) {
            Log.w("MealsStorage.addFood", "FoodInfo not found! id = $foodInfoId")
        }

        realm.executeTransaction {
            val food = it.createObject<Food>()

            food.info = foodInfo
            food.mass = foodMass

            foodList.add(food)
        }

    }

    fun addMeal(daysOffset: Int, mealName: String) {
        val date = Day.parseDate(Calendar.getInstance(), daysOffset)

        realm.executeTransaction {
            val day = getDay(daysOffset)
                ?: it.createObject(date)

            val meal = it.createObject<Meal>()
            meal.name = mealName

            day.meals.add(meal)
        }
    }


    fun getData(daysOffset: Int): List<MealsAdapterData> {

        val day = getDay(daysOffset)
        val out = arrayListOf<MealsAdapterData>()

        if (day != null && day.meals.isNotEmpty()) {

            day.meals.forEachIndexed { i, meal ->
                var kcal = 0f
                var carbs = 0f
                var fats = 0f
                var proteins = 0f

                meal.food.forEach a@{
                    val info = it.info ?: return@a

                    kcal     += NutrientItem.parseNutrientMass(info.calories, it.mass)
                    carbs    += NutrientItem.parseNutrientMass(info.carbs, it.mass)
                    fats     += NutrientItem.parseNutrientMass(info.fats, it.mass)
                    proteins += NutrientItem.parseNutrientMass(info.proteins, it.mass)

                }

                out.add(
                    MealsAdapterData.Header(meal.name, meal.timeOfDay.toString(), kcal)
                )

                meal.food.forEach {
                    out.add(MealsAdapterData.Food(it.info!!.name, it.mass))
                }

                out.add(MealsAdapterData.AddFood(i, carbs, fats, proteins))
            }

        }

        out.add(MealsAdapterData.AddMeal())

        return out
    }

    fun getDayForHeader(daysOffset: Int) = getDay(daysOffset)


//    private fun parseFood(food: RealmList<Food>, out: ArrayList<MealsAdapterData>) {
//        food.forEach {
//            out.add(MealsAdapterData.Food(it.info!!.name, it.mass))
//        }
//    }

    private fun getDay(daysOffset: Int): Day? {
        val date = Day.parseDate(Calendar.getInstance(), daysOffset)

        return realm.where<Day>()
            .equalTo(Day::date.name, date)
            .findFirst()
    }


    companion object {

//        val tempData = listOf(
//            MealsAdapterData.Header("Завтрак", "hello"),
//            MealsAdapterData.Food("some eatable food", 10f),
//            MealsAdapterData.Food("some eatable food 1", 102f),
//            MealsAdapterData.Food("some eatable food 2", 10f),
//            MealsAdapterData.AddFood(0),
//
//            MealsAdapterData.Header("Обэд", "sf"),
//            MealsAdapterData.Food("some eatable food7", 10f),
//            MealsAdapterData.AddFood(1),
//
//            MealsAdapterData.Header("Ужин", "hello2"),
//            MealsAdapterData.Food("some eatable food 1", 10f),
//            MealsAdapterData.Food("some eatable food 4", 10f),
//            MealsAdapterData.AddFood(2),
//
//            MealsAdapterData.AddMeal(),
//        )
    }

}