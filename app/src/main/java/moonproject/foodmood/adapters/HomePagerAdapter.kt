package moonproject.foodmood.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import moonproject.foodmood.ui.DayInfoFragment

class HomePagerAdapter(fragment: Fragment, private val itemsCount: Int) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment {
        val fragment = DayInfoFragment()
        fragment.arguments = Bundle().apply {
            putInt(DayInfoFragment.ARG_CURRENT_POSITION, position - 2)
        }
        return fragment
    }

}