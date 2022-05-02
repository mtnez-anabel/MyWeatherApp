package com.anabelmm.myweatherapp.repository_model

import com.google.gson.annotations.SerializedName

data class CurrentConditionData(
    @SerializedName("LocalObservationDateTime") val localObservationDateTime: String,
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("WeatherText") val weatherPhrase: String,
    @SerializedName("IsDayTime") val isDayTime: Boolean,
    @SerializedName("RealFeelTemperature") val realFeelTemperature: RealFeelTemperature
)
data class Temperature(
    @SerializedName("Metric") val tempMetric: TempMetric
)
data class TempMetric(
    @SerializedName("Value") val tempValueDouble: Double
)
data class RealFeelTemperature(
    @SerializedName("Metric") val tempRealFeelMetric : TempRealFeelMetric
)
data class TempRealFeelMetric(
    @SerializedName("Value") val tempRealFeelValueDouble: Double
)