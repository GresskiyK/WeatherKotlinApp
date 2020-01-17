package com.example.weatherkotlinapp

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.item_for_citiesrecycler.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.InvalidMarkException
import java.text.SimpleDateFormat
import java.util.*
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
    companion object{
        var lattlong=""
        var cityId=""
    }

    fun getFiveDaysWeather(){
        val retrofit = Retrofit.Builder()
            .baseUrl(FirstFragment.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(QueryForAPI::class.java)
        val call = service.getWeather(cityId)
        call.enqueue(object : Callback<WeatherWeekResponse> {
            override fun onResponse(call: Call<WeatherWeekResponse>, response: Response<WeatherWeekResponse>) {
                var weatherResponse=response.body()
                for(i in 1..5){
                    FirstFragment.items.add(ItemOfWeekRecycler(weatherResponse?.weatherList?.get(i)?.iconType.toString(), weatherResponse?.weatherList?.get(i)?.date.toString(),(weatherResponse?.weatherList?.get(i)?.minTemp?.roundToInt()).toString(),(weatherResponse?.weatherList?.get(i)?.maxTemp?.roundToInt()).toString()))
                }
            }
            override fun onFailure(call: Call<WeatherWeekResponse>, t: Throwable) {
                Log.i("error",t.message)
            }
        })
    }
    private fun getWoeidOfCity() {
        val retrofit = Retrofit.Builder()
            .baseUrl(FirstFragment.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(QueryForAPI::class.java)
        val call = service.getWoeid(lattlong)
        Log.i("asd", "asdafs")
        call.enqueue(object : Callback<List<IdOfCity>> {
            override fun onResponse(call: Call<List<IdOfCity>>, response: Response<List<IdOfCity>>) {
                var weatherResponse:List<IdOfCity> = response.body()!!
                cityId =weatherResponse[0].id.toString()
                getFiveDaysWeather()
            }
            override fun onFailure(call: Call<List<IdOfCity>>, t: Throwable) {
                Log.i("error",t.message)
            }
        })

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
                    builder.setTitle("Ops!")
                        .setMessage("Please accept location permission to continue.")
                        .setCancelable(false)
                        .setNegativeButton(
                            "ОК"
                        ) { _, _ -> this.finish() }
                    val alert = builder.create()
                    alert.show()
                } else {
                    getLocation()
                }
            }
        }
    }

    private fun getLocation(){
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lattlong = location?.latitude!!.toString()+","+location.longitude.toString()
                SecondFragment.lat=location?.latitude.toString()
                SecondFragment.lon=location?.longitude.toString()
                setupOfViewPager()
                getWoeidOfCity()
            }
    }

    private fun setupOfViewPager(){
        var vp=findViewById<ViewPager>(R.id.viewpager)
        val fragmentAdapter = PagerAdapter(supportFragmentManager)
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
