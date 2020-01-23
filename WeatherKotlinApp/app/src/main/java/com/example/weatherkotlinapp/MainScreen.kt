package com.example.weatherkotlinapp

import android.Manifest
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.example.weatherkotlinapp.Adapters.PagerAdapter
import com.example.weatherkotlinapp.Fragments.FirstFragment
import com.example.weatherkotlinapp.Fragments.SecondFragment
import com.example.weatherkotlinapp.ItemsOfRecyclers.ItemOfWeekRecycler
import com.example.weatherkotlinapp.Queries.QueriesForApi
import com.example.weatherkotlinapp.Queries.QueryForAPI
import com.example.weatherkotlinapp.WeatherResponse.IdOfCity
import com.example.weatherkotlinapp.WeatherResponse.WeatherWeekResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt


class MainScreen : AppCompatActivity() {

    private lateinit var dotsLayout:LinearLayout
    private var layouts= arrayOf(R.layout.first_fragment,R.layout.second_fragment,R.layout.third_fragment)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                0
            )
        }
        onRequestPermissionsResult(0,arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), intArrayOf(PackageManager.PERMISSION_GRANTED))
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun checkInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Notification")
                        .setMessage("Please accept location permission to continue.")
                        .setCancelable(false)
                        .setNegativeButton(
                            "ОК"
                        ) { _, _ -> this.finish() }
                    val alert = builder.create()
                    alert.show()
                } else if(!checkInternet(this)){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Internet connection")
                        .setMessage("Please check Internet connection.")
                        .setCancelable(false)
                        .setNegativeButton(
                            "ОК"
                        ) { _, _ -> this.finish() }
                    val alert = builder.create()
                    alert.show()
                }
                else{
                    QueriesForApi().getLocation(this)
                }
            }
        }
    }

    fun setupOfViewPager(){
        val vp=findViewById<ViewPager>(R.id.viewpager)
        val fragmentAdapter =
            PagerAdapter(supportFragmentManager)
        vp.adapter = fragmentAdapter
        vp.currentItem = 1
        dotsLayout=findViewById(R.id.dotsLayout)
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
}
