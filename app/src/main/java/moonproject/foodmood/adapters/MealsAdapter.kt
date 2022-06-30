package moonproject.foodmood.adapters

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_meal_add_food.*
import kotlinx.android.synthetic.main.item_meal_add_meal.*
import kotlinx.android.synthetic.main.item_meal_food.*
import kotlinx.android.synthetic.main.item_meal_header.*
import moonproject.foodmood.R
import moonproject.foodmood.extensions.inflate
import moonproject.foodmood.extensions.onClick
import moonproject.foodmood.extensions.text
import moonproject.foodmood.models.MealsAdapterData

class MealsAdapter(
    private val clickActions: MealsClickActions
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val data: ArrayList<MealsAdapterData> = arrayListOf()


    inner class HeaderVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(data: MealsAdapterData.Header) {
            titleTv.text = data.title
            kcalTv.text = String.format("<big>%.0f</big>%s", data.kcal, "ккал").parseAsHtml()
        }

    }


    inner class FoodVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(data: MealsAdapterData.Food) {
            foodName.text = data.food
            massTv.text = String.format("%.0f %s", data.mass, "г")
        }
    }


    inner class AddFoodVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(data: MealsAdapterData.AddFood) {
            carbsTv   .text = String.format("%.0f %s", data.carbs, "г")
            fatsTv    .text = String.format("%.0f %s", data.fats, "г")
            proteinsTv.text = String.format("%.0f %s", data.proteins, "г")

            addFoodBtn.onClick {
                clickActions.addFood(data.mealIndex)
            }

            infoBtn.onClick {

            }
        }
    }


    inner class AddMealVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind() {
            addMealBtn.onClick {
                addMealDialog()
            }
            useTemplateBtn.onClick {

            }
            copyDayBtn.onClick {

            }
        }


        private fun addMealDialog() {
            val d = MaterialAlertDialogBuilder(containerView.context)
                .setView(R.layout.dialog_add_meal)
                .setPositiveButton(android.R.string.ok, null)
                .show()

            val field = d.findViewById<TextInputLayout>(R.id.inputField)!!

            d.getButton(AlertDialog.BUTTON_POSITIVE)!!.onClick {
                val mealName = field.text

                clickActions.addMeal(mealName, this@MealsAdapter)
                d.cancel()
            }

        }
    }



    fun updateData(newData: List<MealsAdapterData>) {
        data.clear()
        data.addAll(newData)

        notifyDataSetChanged()
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
            is MealsAdapterData.Header  -> (holder as HeaderVH).bind(d)
            is MealsAdapterData.Food    -> (holder as FoodVH).bind(d)
            is MealsAdapterData.AddFood -> (holder as AddFoodVH).bind(d)
            is MealsAdapterData.AddMeal -> (holder as AddMealVH).bind()
        }
    }

    override fun getItemViewType(position: Int) = data[position].type
    override fun getItemCount(): Int = data.size

}