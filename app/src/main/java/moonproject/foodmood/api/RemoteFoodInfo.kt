package moonproject.foodmood.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import moonproject.foodmood.models.realm.FoodInfo
import moonproject.foodmood.ui.FoodInfoFragmentArgs
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class RemoteFoodInfo(private val coroutineScope: CoroutineScope) {


    suspend fun getDataFromRemote(args: FoodInfoFragmentArgs) =
        withContext(coroutineScope.coroutineContext + Dispatchers.Default) {
            val doc = Jsoup.connect("https://fitaudit.ru/food/${args.foodInfoId}").get()
            val foodInfo = FoodInfo()

            val data = doc.select(".js__msr_cc")

            foodInfo.id = args.foodInfoId
            foodInfo.name = args.foodName
            foodInfo.description = args.foofDescription

            foodInfo.calories = data[2].parse()
            foodInfo.fats = data[3].parse()
            foodInfo.proteins = data[4].parse()
            foodInfo.carbs = data[5].parse()
            foodInfo.water = data[6].parse()

//        log("ccal", foodInfo.id)
//        log("ccal", foodInfo.name)
//        log("ccal", foodInfo.description)
//        log("ccal", foodInfo.calories)
//        log("ccal", foodInfo.fats)
//        log("ccal", foodInfo.proteins)
//        log("ccal", foodInfo.carbs)
//        log("ccal", foodInfo.water)

//        _foodInfo.postValue(foodInfo)
            foodInfo
        }


    private fun Element.parse(): Float = this.attr("data-fa").let {
        if (it.isNotEmpty()) Gson().fromJson(it, CalInfo::class.java).value
        else 0f
    }



    private data class CalInfo(
        private val msr: CalInfoInfo
    ) {
        val value: Float
            get() = msr.value
    }

    private data class CalInfoInfo(
        @SerializedName("val") val value: Float
    )

}