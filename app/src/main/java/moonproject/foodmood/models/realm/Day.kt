package moonproject.foodmood.models.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Day(
    @PrimaryKey
    var date: Long,        //millis. дата без времени (т.е. дата в полночь)

    var meals: RealmList<Meal>
) : RealmObject() {
    constructor() : this(0L, RealmList())


    companion object {
        fun parseDate(calendar: Calendar, daysOffset: Int): Long {
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.HOUR, 0)

            calendar.add(Calendar.DAY_OF_YEAR, daysOffset)

            return calendar.timeInMillis
        }
    }
}