package moonproject.foodmood.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_day_info.*
import moonproject.foodmood.R
import moonproject.foodmood.adapters.MealsAdapter
import moonproject.foodmood.adapters.MealsClickActions

class DayInfoFragment : Fragment(R.layout.fragment_day_info) {


    private val viewModel: MainViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val daysOffset = arguments?.getInt(ARG_CURRENT_POSITION) ?: throw NullPointerException("daysOffset arg not found!")

        val mealsClickActions = object : MealsClickActions {

            override fun addFood(mealIndex: Int) {
                viewModel.initNewFoodParams(daysOffset, mealIndex)
                findNavController().navigate(R.id.addFoodDialog)
            }

            override fun addMeal(mealName: String, mealsAdapter: MealsAdapter) {
                viewModel.addMeal(daysOffset, mealName)

                mealsAdapter.updateData(
                    viewModel.getData(daysOffset)
                )

            }
        }

        val adapter = MealsAdapter(mealsClickActions)

        adapter.updateData(
            viewModel.getData(daysOffset)
        )

        dayInfoList.adapter = adapter
    }


    companion object {
        const val ARG_CURRENT_POSITION = "ARG_CURRENT_POSITION"
    }

}