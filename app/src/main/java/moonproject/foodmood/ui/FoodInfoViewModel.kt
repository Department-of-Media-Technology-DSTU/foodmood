package moonproject.foodmood.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import moonproject.foodmood.models.realm.FoodInfo

class FoodInfoViewModel : ViewModel() {


    val foodInfo = MutableLiveData<FoodInfo>()
//    val foodInfo: LiveData<FoodInfo> = _foodInfo

}