package com.example.weatherkotlinapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.graphics.toColorInt
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import android.view.View


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.textViewStart)

        val word = SpannableString(getString(R.string.enter_name))

        word.setSpan(
            ForegroundColorSpan(Color.RED),
            10,
            word.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = word
    }

    fun continueWithName(view: View) {
        val intent=Intent(this,MainScreen::class.java)
        startActivity(intent)
    }
}
