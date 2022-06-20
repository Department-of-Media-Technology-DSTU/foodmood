package moonproject.foodmood.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FoodInfo(
    @PrimaryKey
    var id: Long,

    var name: String,
    var description: String,
//    var comment: String,

    //ед. изм. грамм/100грамм
    var proteins: Float,        //белки
    var fats: Float,            //жиры
    var carbs: Float,           //углеводы
    var water: Float,

    //ед. изм. ккал
    var calories: Float,

) : RealmObject() {
    constructor() : this(0L, "", "", 0f, 0f, 0f, 0f, 0f)
}