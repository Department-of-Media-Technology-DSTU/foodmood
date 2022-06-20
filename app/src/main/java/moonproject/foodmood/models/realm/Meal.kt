package moonproject.foodmood.models.realm

import io.realm.RealmList
import io.realm.RealmObject
import moonproject.foodmood.models.realm.Food

open class Meal(
    var name: String,
    var timeOfDay: Long,        //millis. ноль это полночь

    var food: RealmList<Food>
) : RealmObject() {
    constructor() : this("", 0L, RealmList())
}