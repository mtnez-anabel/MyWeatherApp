package com.anabelmm.myweatherapp.repository_model

import com.anabelmm.myweatherapp.repository_model.db.FixedData

class WeatherDataRepository() {
    private val api: DataManagerAPIClient = DataManagerAPIClient()

    suspend fun getWeatherData(): DataManagerAPIClient.WeatherData {
        return getWeatherDataFromApi() ?:  //if I exceeded my 50 calls per day the Weather data from Api will be null
        return getWeatherDataFromDB()      //then return the Weather data from a data base
    }

    private suspend fun getWeatherDataFromApi(): DataManagerAPIClient.WeatherData? = api.getAllWeatherData()

    private suspend fun getWeatherDataFromDB():DataManagerAPIClient.WeatherData{
        return FixedData.fixedData
    }
}