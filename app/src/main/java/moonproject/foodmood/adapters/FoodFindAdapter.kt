package moonproject.foodmood.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_food_find.*
import moonproject.foodmood.R
import moonproject.foodmood.api.RemoteSearchData
import moonproject.foodmood.extensions.inflate
import moonproject.foodmood.extensions.onClick

class FoodFindAdapter(
    private val onItemClicked: (id: Long, name: String, description: String) -> Unit
) : RecyclerView.Adapter<FoodFindAdapter.VH>() {


    private val data = arrayListOf<RemoteSearchData>()


    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(obj: RemoteSearchData) {
            val note =
                if (obj.note != null) "(${obj.note})"
                else ""

            val description =
                if (obj.description.isNullOrEmpty()) note
                else obj.description + " " + note

            nameTv.text = obj.name
            descriptionTv.text = description

            containerView.onClick {
                onItemClicked(obj.id, obj.name, description)
            }
        }

    }


    fun updateData(newData: List<RemoteSearchData>) {
        data.clear()
        data.addAll(newData)

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(parent.inflate(R.layout.item_food_find))
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(data[position])
    override fun getItemCount(): Int = data.size

}