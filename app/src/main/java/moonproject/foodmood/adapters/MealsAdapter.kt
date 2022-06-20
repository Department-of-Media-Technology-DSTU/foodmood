package moonproject.foodmood.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_meal_add_food.*
import kotlinx.android.synthetic.main.item_meal_add_meal.*
import kotlinx.android.synthetic.main.item_meal_food.*
import moonproject.foodmood.R
import moonproject.foodmood.extensions.inflate
import moonproject.foodmood.extensions.onClick
import moonproject.foodmood.models.MealsAdapterData

class MealsAdapter(private val data: List<MealsAdapterData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class HeaderVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(mealName: String) {
            (containerView as TextView).text = mealName
        }

    }


    inner class FoodVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(data: String) {
            foodName.text = data
        }
    }


    inner class AddFoodVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind() {
            addFoodBtn.onClick {

            }
        }
    }


    inner class AddMealVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind() {
            addMealBtn.onClick {

            }
            useTemplateBtn.onClick {

            }
            copyDayBtn.onClick {

            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            MealsAdapterData.TYPE_HEADER   -> HeaderVH(parent.inflate(R.layout.item_meal_header))
            MealsAdapterData.TYPE_FOOD     -> FoodVH(parent.inflate(R.layout.item_meal_food))
            MealsAdapterData.TYPE_ADD_FOOD -> AddFoodVH(parent.inflate(R.layout.item_meal_add_food))
            MealsAdapterData.TYPE_ADD_MEAL -> AddMealVH(parent.inflate(R.layout.item_meal_add_meal))
            else -> throw IllegalStateException("Unknown viewType: $viewType")
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val d = data[position]) {
            is MealsAdapterData.Header  -> (holder as HeaderVH).bind(d.title)
            is MealsAdapterData.Food    -> (holder as FoodVH).bind(d.food)
            is MealsAdapterData.AddFood -> (holder as AddFoodVH).bind()
            is MealsAdapterData.AddMeal -> (holder as AddMealVH).bind()
        }
    }

    override fun getItemViewType(position: Int) = data[position].type
    override fun getItemCount(): Int = data.size

}