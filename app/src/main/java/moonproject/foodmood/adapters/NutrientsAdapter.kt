package moonproject.foodmood.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import moonproject.foodmood.R
import moonproject.foodmood.extensions.inflate
import moonproject.foodmood.models.NutrientItem

class NutrientsAdapter : RecyclerView.Adapter<NutrientsAdapter.VH>() {


    private val data = arrayListOf<NutrientItem>()

    var currentMass: Float = NutrientItem.BASE_MASS
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(obj: NutrientItem) {
            val tv = containerView as TextView

            tv.text = String.format(
                "<b>%s:</b> %.2f %s",
                obj.title,
                NutrientItem.parseNutrientMass(obj.data, currentMass),
                obj.currency
            ).parseAsHtml()
        }

    }



    fun updateData(newData: List<NutrientItem>) {
        data.clear()
        data.addAll(newData)

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(parent.inflate(R.layout.item_food_info))
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(data[position])
    override fun getItemCount(): Int = data.size


}