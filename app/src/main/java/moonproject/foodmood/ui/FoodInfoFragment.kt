package moonproject.foodmood.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_food_info.*
import moonproject.foodmood.R
import moonproject.foodmood.adapters.NutrientsAdapter
import moonproject.foodmood.extensions.adapter
import moonproject.foodmood.extensions.onClick
import moonproject.foodmood.extensions.text
import moonproject.foodmood.models.NutrientItem
import moonproject.foodmood.models.realm.FoodInfo

class FoodInfoFragment : Fragment(R.layout.fragment_food_info) {


    private val viewModel: FoodInfoViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: FoodInfoFragmentArgs by navArgs()
        val adapter = NutrientsAdapter()

        nutrientsList.adapter = adapter
        nutrientsList.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )

        massField.editText!!.doAfterTextChanged {
            val mass = it!!.toString().let { t ->
                if (t.isEmpty()) NutrientItem.BASE_MASS
                else t.toFloatOrNull() ?: NutrientItem.BASE_MASS
            }

            adapter.currentMass = mass
        }

        cancelBtn.onClick {
            findNavController().navigate(
                FoodInfoFragmentDirections.actionFoodInfoFragmentToHomeFragment()
            )
        }

        saveBtn.isEnabled = false
        saveBtn.onClick {
            mainViewModel.saveFoodInfoToRealm(
                viewModel.foodInfo.value!!
            )

            mainViewModel.addFood(
                args.foodInfoId,
                massField.text.toFloatOrNull() ?: NutrientItem.BASE_MASS,
                mainViewModel.addNewFoodParams!!.mealIndex,
                mainViewModel.addNewFoodParams!!.daysOffset
            )

            mainViewModel.clearNewFoodParams()

            findNavController().navigate(
                FoodInfoFragmentDirections.actionFoodInfoFragmentToHomeFragment()
            )
        }

        viewModel.foodInfo.observe(viewLifecycleOwner) {
            saveBtn.isEnabled = it != null
            initScreen(it)
        }

        mainViewModel.loadFoodById(args) {
            viewModel.foodInfo.value = it
        }
    }

    private fun initScreen(foodInfo: FoodInfo) {
        toolbar.title = foodInfo.name
        toolbar.subtitle = foodInfo.description

        nutrientsList.adapter<NutrientsAdapter>().updateData(
            NutrientItem.parseFromFoodInfo(foodInfo)
        )
    }

}
