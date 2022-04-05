package com.anabelmm.myweatherapp.repository_model

import com.anabelmm.myweatherapp.repository_model.db.FixedData

class WeatherDataRepository() {
    private val api: DataManagerAPIClient = DataManagerAPIClient()
    suspend fun getWeatherDataFromApi(): DataManagerAPIClient.WeatherData {
        val result = api.getAllWeatherData()
        if (result.currTemperature == null) //if I exceeded my 50 calls per day
            return FixedData.fixedData
        return result
    }
}