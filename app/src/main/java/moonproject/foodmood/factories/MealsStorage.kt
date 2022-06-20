package moonproject.foodmood.factories

import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where
import moonproject.foodmood.models.MealsAdapterData
import moonproject.foodmood.models.realm.Day
import moonproject.foodmood.models.realm.Food
import java.util.*

class MealsStorage(private val realm: Realm) {




    fun getData(daysOffset: Int): List<MealsAdapterData> {
        val date = Day.parseDate(Calendar.getInstance(), daysOffset)

        val day = realm.where<Day>()
            .equalTo(Day::date.name, date)
            .findFirst()

        val out = arrayListOf<MealsAdapterData>()

        if (day != null && day.meals.isNotEmpty()) {

            day.meals.forEach { meal ->
                out.add(MealsAdapterData.Header(meal.name, meal.timeOfDay.toString()))

                parseFood(meal.food, out)

                out.add(MealsAdapterData.AddFood())
            }

        }

        out.add(MealsAdapterData.AddMeal())

        return out
    }

    private fun parseFood(food: RealmList<Food>, out: ArrayList<MealsAdapterData>) {
        food.forEach {
            out.add(MealsAdapterData.Food(it.info!!.name + " " + it.mass))
        }
    }

}