package com.example.weatherkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.nio.InvalidMarkException


class MainScreen : AppCompatActivity() {

    private lateinit var Dots_Layout:LinearLayout
    lateinit var dots:Array<ImageView>


    private var layouts= arrayOf(R.layout.first_fragment,R.layout.second_fragment,R.layout.third_fragment)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        var vp=findViewById<ViewPager>(R.id.viewpager)
        val fragmentAdapter = PagerAdapter(supportFragmentManager)
        vp.adapter = fragmentAdapter
        vp.currentItem = 1
        //tabs.setupWithViewPager(vp)
        Dots_Layout=findViewById(R.id.dotsLayout)
        createDots()
        setCurrentIndicator(1)



        vp?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                setCurrentIndicator(position)
            }

        })
    }

    private fun createDots(){
        val indicators= arrayOfNulls<ImageView>(layouts.size)
        val layoutParams:LinearLayout.LayoutParams=LinearLayout.LayoutParams(WRAP_CONTENT,
            WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)

        for(i in indicators.indices){
            indicators[i]= ImageView(applicationContext)
            indicators[i].apply { this?.setImageDrawable(
                ContextCompat.getDrawable(
                applicationContext,R.drawable.default_dots

                )
             )
                this?.layoutParams=layoutParams
            }
            dotsLayout.addView(indicators[i])
        }


    }
    private fun setCurrentIndicator(index:Int){
        val childCount=dotsLayout.childCount
        for(i in 0 until childCount){
            val imageView=dotsLayout.get(i) as ImageView
            if(i==index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                            applicationContext,R.drawable.dots_selected
                            )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,R.drawable.default_dots
                    ))
            }
        }
    }

//    private fun setupTabIcons() {
//        tabs.getTabAt(0)?.setIcon(tabIcons[0])
//        tabs.getTabAt(1)?.setIcon(tabIcons[1])
//        tabs.getTabAt(2)?.setIcon(tabIcons[2])
//    }
}
