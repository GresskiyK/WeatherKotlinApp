package com.example.weatherkotlinapp.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.weatherkotlinapp.Fragments.FirstFragment
import com.example.weatherkotlinapp.Fragments.SecondFragment
import com.example.weatherkotlinapp.Fragments.ThirdFragment


class PagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

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
}