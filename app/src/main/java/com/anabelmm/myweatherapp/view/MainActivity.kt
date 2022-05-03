package com.anabelmm.myweatherapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.MeasureFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.anabelmm.myweatherapp.databinding.ActivityMainBinding
import com.anabelmm.myweatherapp.databinding.DailyInfoItemBinding
import com.anabelmm.myweatherapp.databinding.HourlyInfoItemBinding
import com.anabelmm.myweatherapp.repository_model.DataManagerAPIClient
import com.anabelmm.myweatherapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    private val mf = MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.SHORT)
    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var bindingDailyInfo: DailyInfoItemBinding
    private lateinit var bindingHourlyInfo: HourlyInfoItemBinding
    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(
            this,
            factory
        )[WeatherViewModel::class.java]
    }
    var factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WeatherViewModel(this@MainActivity.applicationContext, lifecycleScope) as T
        }
    }
    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        lifecycleScope.launch {
            weatherViewModel.onCreate()
            weatherViewModel.weatherModel.observe(this@MainActivity, Observer {
                initConfig(it)
            })
        }
        bindingMain.websiteText.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.accuweather.com")
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initConfig(wd: DataManagerAPIClient.WeatherData) {
        bindingMain.cityText.text = getCity()
        bindingMain.currTempText.text =
            wd.currTemperature?.let { it1 -> setCurrentTemperature(it1) }
        bindingMain.weatherPhraseText.text = wd.currWeatherPhrase
        bindingMain.realFeelText.text =
            wd.realFeelTemperature?.let { it1 -> setCurrentRealFeel(it1) }
        bindingMain.weekdayText.text = getWeekdayToday()
        var minT = wd.list5DaysWeather[0].minValue!!
        var maxT = wd.list5DaysWeather[0].maxValue!!
        bindingMain.maxAndMinText.text = setMaxAndMinTemperature(minT, maxT)

        if (bindingMain.linearHorizontalLayout.size == 0) {
            for (i in 0..11) {
                bindingHourlyInfo = HourlyInfoItemBinding.inflate(layoutInflater)
                val epoc = wd.list12HWeather[i].epochDateTime!!
                bindingHourlyInfo.hourItemText.text = getEachHour(epoc)
                val eachTemp = wd.list12HWeather[i].hourlyTempValue!!
                bindingHourlyInfo.temperatureItemText.text =
                    mf.format(Measure(eachTemp, MeasureUnit.CELSIUS))
                bindingMain.linearHorizontalLayout.addView(bindingHourlyInfo.root)
            }
            for (i in 1..4) {
                bindingDailyInfo = DailyInfoItemBinding.inflate(layoutInflater)
                bindingDailyInfo.dayTextView.text = getWeekday(i)
                minT = wd.list5DaysWeather[i].minValue!!
                maxT = wd.list5DaysWeather[i].maxValue!!
                bindingDailyInfo.maxAndMinDayTextView.text = setMaxAndMinTemperature(minT, maxT)
                bindingMain.forecastFiveDaysLinearLayout.addView(bindingDailyInfo.root)
            }
        }
    }


    private fun getCity(): String {
        return "Milan,Italy" //TODO: To get the city using the device location
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setCurrentTemperature(d: Double): String {
        val measure = Measure(d, MeasureUnit.CELSIUS)
        return "     " + mf.format(measure)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setCurrentRealFeel(d: Double): String {
        val measure = Measure(d, MeasureUnit.CELSIUS)
        return "Real Feel: " + mf.format(measure)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getWeekdayToday(): String {
        val date = Calendar.getInstance().time
        val str = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        return "$str  "
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setMaxAndMinTemperature(min: Double, max: Double): String {
        val measureMin = Measure(min, MeasureUnit.CELSIUS)
        val measureMax = Measure(max, MeasureUnit.CELSIUS)
        return mf.format(measureMax) + " Max" + "/ " + mf.format(measureMin) + " Min"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEachHour(e: Long): String {
        val instant = Instant.ofEpochSecond(e)
        var hour = instant.atZone(ZoneId.systemDefault()).hour
        return when {
            hour == 12 -> "12:00PM"
            hour == 0 -> "12:00AM"
            hour > 12 -> {
                hour -= 12
                "$hour:00PM"
            }
            else -> "$hour:00AM"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeekday(i: Int): String {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val localDateTime =
            LocalDateTime.from(date.toInstant().atZone(ZoneId.of("UTC"))).plusDays(i.toLong())
        return localDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    }
}

