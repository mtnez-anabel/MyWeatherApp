package com.anabelmm.myweatherapp.repository_model

import com.google.gson.annotations.SerializedName

data class Forecast12HData(
    @SerializedName("EpochDateTime") val epochDateTime: Long,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
    @SerializedName("Temperature") val hourlyTemperature: HourlyTemperature
)
data class HourlyTemperature(
    @SerializedName("Value") val hourlyTempValue : Double
)
