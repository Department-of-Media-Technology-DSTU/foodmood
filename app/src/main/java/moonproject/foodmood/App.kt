package moonproject.foodmood

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {


    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val realmConfiguration = RealmConfiguration.Builder()
            .name("diary.realm")
//            .migration(MyRealmMigration())
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }


    companion object {
//        var lockPrefs: Prefs? = null
    }

}


