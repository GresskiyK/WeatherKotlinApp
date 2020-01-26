package com.example.weatherkotlinapp

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible

class ProgressBar{

    companion object{
        fun visible(progressBar:ProgressBar){
            progressBar.isVisible=true
        }
        fun disable(progressBar: ProgressBar){
            progressBar.isVisible=false
        }
    }

}