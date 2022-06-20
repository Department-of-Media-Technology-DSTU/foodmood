package moonproject.foodmood.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import moonproject.foodmood.R
import moonproject.foodmood.adapters.HomePagerAdapter
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabsCount = 5

        val dates = Array(tabsCount) { "" }
        val calendar = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("dd.MM", Locale.getDefault())

        repeat(tabsCount) {
            calendar.add(Calendar.DAY_OF_YEAR, it - 2)

            dates[it] = dateFormatter.format(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -(it - 2))
        }

        dates[1] = "Вчера"
        dates[2] = "Сегодня"
        dates[3] = "Завтра"


        pager.adapter = HomePagerAdapter(this, tabsCount)
        pager.setCurrentItem(2, false)

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = dates[position]
        }.attach()

    }
}