package com.anabelmm.myweatherapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.anabelmm.myweatherapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun openLink(view: View?) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.accuweather.com")
            )
        )
    }
}

