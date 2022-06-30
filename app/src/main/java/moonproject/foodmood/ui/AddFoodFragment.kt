package moonproject.foodmood.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_add_food.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moonproject.foodmood.R
import moonproject.foodmood.adapters.FoodFindAdapter
import moonproject.foodmood.api.RemoteSearch

class AddFoodFragment : Fragment(R.layout.dialog_add_food) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var job: Job? = null

        val adapter = FoodFindAdapter { id, name, description ->
            val arg = AddFoodFragmentDirections.actionAddFoodDialogToFoodInfoFragment(id, name, description)
            findNavController().navigate(arg)
        }

        searchField.editText!!.doAfterTextChanged {
            val text = it!!.toString().trim()
            if (text.isEmpty()) return@doAfterTextChanged

            job?.cancel()
            job = startSearch(text, adapter)
        }

        foodList.adapter = adapter

        foodList.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )

    }


    private fun startSearch(text: String, adapter: FoodFindAdapter): Job {
        return lifecycleScope.launch {
            delay(500)

            val results = RemoteSearch.getService().search(text)

            launch(Dispatchers.Main) {

                nothingFoundTv.isVisible  = results.isEmpty()
                adapter.updateData(results)

//                log("data", results.joinToString { it.toString() })

            }
        }
    }


}