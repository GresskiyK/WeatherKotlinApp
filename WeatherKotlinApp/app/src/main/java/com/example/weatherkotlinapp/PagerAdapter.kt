package com.example.weatherkotlinapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import android.text.Spannable
import android.text.style.ImageSpan
import android.text.SpannableString
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import android.R
import android.view.View
import android.widget.RelativeLayout




class PagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<Fragment> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {

                FirstFragment()
            }
            1 -> {
                SecondFragment()
            }
            2 -> {
                ThirdFragment()

            }
            else -> {
                return SecondFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }


    override fun getPageTitle(position: Int): CharSequence? {
//        return when (position) {
//            0 -> {
//                ""
//            }
//            1 -> {
//                "Current Weather"
//            }
//            2 -> {
//                "Third"
//            }
//            else -> {
//                return "Default"
//            }
//        }
        return null
    }
}