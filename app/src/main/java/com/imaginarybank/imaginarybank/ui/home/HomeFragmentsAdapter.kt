package com.imaginarybank.imaginarybank.ui.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.ListFragment
import com.imaginarybank.imaginarybank.ui.list.ListLocationsFragment
import com.imaginarybank.imaginarybank.ui.my_location.MyLocationFragment

/**
 * Created by RinaSelmani on 17-Feb-19.
 */

class HomeFragmentsAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    lateinit var fragment: Fragment
    val FRAGMENT_COUNT: Int = 2
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                fragment = MyLocationFragment()
            }
            1 -> {
                fragment = ListLocationsFragment()
            }


        }

        return fragment
    }

    override fun getCount(): Int {
        return FRAGMENT_COUNT
    }
}