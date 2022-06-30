package moonproject.foodmood.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moonproject.foodmood.api.RemoteFoodInfo
import moonproject.foodmood.models.AddNewFoodParams
import moonproject.foodmood.models.MealsAdapterData
import moonproject.foodmood.models.realm.Day
import moonproject.foodmood.models.realm.FoodInfo
import moonproject.foodmood.storages.MealsStorage

class MainViewModel : ViewModel() {


    private val realm = Realm.getDefaultInstance()
    private val storage = MealsStorage(realm)
    private val remoteFoodInfo = RemoteFoodInfo(viewModelScope)

    var addNewFoodParams: AddNewFoodParams? = null
        private set

    var currentPagerItem = 2


    fun initNewFoodParams(daysOffset: Int, mealIndex: Int) {
        addNewFoodParams = AddNewFoodParams(daysOffset, mealIndex)
    }

    fun addToNewFood(mass: Float, foodInfoId: Long) {
        addNewFoodParams?.let {
            it.mass = mass
            it.foodInfoId = foodInfoId
            it.shouldUpdate = true
        }
    }

    fun clearNewFoodParams() {
        addNewFoodParams = null
    }

    fun getData(daysOffset: Int): List<MealsAdapterData> {
        return storage.getData(daysOffset)
    }

    //нильзя так но ...
    fun getDayForHeader(daysOffset: Int): Day? {
        return storage.getDayForHeader(daysOffset)
    }

    fun addMeal(daysOffset: Int, mealName: String) {
        storage.addMeal(daysOffset, mealName)
    }

    fun addFood(foodInfoId: Long, foodMass: Float, mealIndex: Int, daysOffset: Int) {
        storage.addFood(foodInfoId, foodMass, mealIndex, daysOffset)
    }

    fun saveFoodInfoToRealm(foodInfo: FoodInfo) {
        storage.saveFoodInfoToRealm(foodInfo)
    }


    fun loadFoodById(foodInfoArgs: FoodInfoFragmentArgs, callback: (FoodInfo) -> Unit) {
        val cachedFoodInfo = storage.getFoodInfo(foodInfoArgs.foodInfoId)

        if (cachedFoodInfo != null) {
            callback(cachedFoodInfo)
        } else {
            viewModelScope.launch (Dispatchers.Main) {
                val f = remoteFoodInfo.getDataFromRemote(foodInfoArgs)
                callback(f)
            }
        }
    }


    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

}