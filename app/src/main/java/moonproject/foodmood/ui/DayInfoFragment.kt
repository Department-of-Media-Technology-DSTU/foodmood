package moonproject.foodmood.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_day_info.*
import moonproject.foodmood.R
import moonproject.foodmood.adapters.MealsAdapter
import moonproject.foodmood.models.MealsAdapterData

class DayInfoFragment : Fragment(R.layout.fragment_day_info) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val daysOffset = arguments?.getInt(ARG_CURRENT_POSITION) ?: throw NullPointerException("daysOffset arg not found!")

        val mealsData = listOf(
            MealsAdapterData.Header("Завтрак"),
            MealsAdapterData.Food("some eatable food"),
            MealsAdapterData.Food("some eatable food 1"),
            MealsAdapterData.Food("some eatable food 2"),
            MealsAdapterData.AddFood(),

            MealsAdapterData.Header("Обэд"),
            MealsAdapterData.Food("some eatable food7"),
            MealsAdapterData.AddFood(),

            MealsAdapterData.Header("Ужин"),
            MealsAdapterData.Food("some eatable food 1"),
            MealsAdapterData.Food("some eatable food 4"),
            MealsAdapterData.AddFood(),

            MealsAdapterData.AddMeal(),
        )
        val adapter = MealsAdapter(mealsData)

        dayInfoList.adapter = adapter
    }


    companion object {
        const val ARG_CURRENT_POSITION = "ARG_CURRENT_POSITION"
    }

}