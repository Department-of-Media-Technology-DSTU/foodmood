package moonproject.foodmood.ui

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.color.MaterialColors
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_fragment_home_header.*
import moonproject.foodmood.R
import moonproject.foodmood.adapters.HomePagerAdapter
import moonproject.foodmood.models.NutrientItem
import moonproject.foodmood.models.realm.Day
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HomeFragment : Fragment(R.layout.fragment_home) {


    private val viewModel: MainViewModel by activityViewModels()


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

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val daysOffset = HomePagerAdapter.parseDaysOffset(position)

                viewModel.currentPagerItem = position
                initHeader(
                    daysOffset, viewModel.getDayForHeader(daysOffset)
                )
            }
        })

        pager.setCurrentItem(viewModel.currentPagerItem, false)

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = dates[position]
        }.attach()

    }


    private fun initHeader(daysOffset: Int, day: Day?) {
        var consumedKCal = 0f
        var carbs = 0f
        var fats = 0f
        var proteins = 0f

        val goalKcal = 2900f
        val goalCarbs = 180f
        val goalFats = 90f
        val goalProteins = 120f

        val time = Calendar.getInstance().let {
            it.add(Calendar.DAY_OF_YEAR, daysOffset)
            it.time
        }

        day?.meals?.forEach a@{ meal ->

            meal.food.forEach {
                val info = it.info ?: return@a

                consumedKCal += NutrientItem.parseNutrientMass(info.calories, it.mass)
                carbs        += NutrientItem.parseNutrientMass(info.carbs, it.mass)
                fats         += NutrientItem.parseNutrientMass(info.fats, it.mass)
                proteins     += NutrientItem.parseNutrientMass(info.proteins, it.mass)

            }

        }

        kcalGoalTv.text = parseKcal(goalKcal)
        kcalConsumedTv.text = parseKcal(consumedKCal)
        kcalRemainingTv.text = parseKcal(goalKcal - consumedKCal)

        carbsProgressTv.text = parseNutrient(carbs, goalCarbs)
        fatsProgressTv.text = parseNutrient(fats, goalFats)
        proteinsProgressTv.text = parseNutrient(proteins, goalProteins)

        carbsProgressBar.progress = (carbs / goalCarbs * 100).roundToInt()
        fatsProgressBar.progress = (fats / goalFats * 100).roundToInt()
        proteinsProgressBar.progress = (proteins / goalProteins * 100).roundToInt()

        carbsProgressBar.setIndicatorColor(MaterialColors.harmonizeWithPrimary(requireContext(), 0xfffc7828.toInt()))
        fatsProgressBar.setIndicatorColor(MaterialColors.harmonizeWithPrimary(requireContext(), 0xff471eaf.toInt()))
        proteinsProgressBar.setIndicatorColor(MaterialColors.harmonizeWithPrimary(requireContext(), 0xff1cbbdd.toInt()))

        dateTv.text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(time)
    }


    private fun parseKcal(kcal: Float) = String.format("<big>%.0f</big>%s", kcal, "ккал").parseAsHtml()
    private fun parseNutrient(current: Float, goal: Float) =
        String.format("<b>%.0f</b> / %.0f %s", current, goal, "г")
            .parseAsHtml()

}