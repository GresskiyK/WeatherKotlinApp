package com.example.weatherkotlinapp

import android.Manifest
import android.content.DialogInterface
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
import java.nio.InvalidMarkException


class MainScreen : AppCompatActivity() {

    private lateinit var Dots_Layout:LinearLayout
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
                FirstFragment.lattlong = location?.latitude!!.toString()+","+location.longitude.toString()
                SecondFragment.lat=location?.latitude.toString()
                SecondFragment.lon=location?.longitude.toString()
                setupOfViewPager()
            }
    }

    private fun setupOfViewPager(){
        var vp=findViewById<ViewPager>(R.id.viewpager)
        val fragmentAdapter = PagerAdapter(supportFragmentManager)
        vp.adapter = fragmentAdapter
        vp.currentItem = 1
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
}
