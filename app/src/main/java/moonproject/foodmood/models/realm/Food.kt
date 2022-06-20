package moonproject.foodmood.models.realm

import io.realm.RealmObject

open class Food(
    //грамм
    var mass: Float,
    var info: FoodInfo?
) : RealmObject() {
    constructor() : this(0f, null)
}

