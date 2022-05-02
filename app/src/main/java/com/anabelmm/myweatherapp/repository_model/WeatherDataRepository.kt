package com.anabelmm.myweatherapp.repository_model

import android.content.Context
import com.anabelmm.myweatherapp.repository_model.db.DataBaseManager
import com.anabelmm.myweatherapp.repository_model.db.FixedData
import com.anabelmm.myweatherapp.repository_model.db.WeatherDao
import com.anabelmm.myweatherapp.repository_model.db.WeatherDataBase
import kotlinx.coroutines.*

class WeatherDataRepository(context: Context, scope: CoroutineScope) {
    private val api: DataManagerAPIClient = DataManagerAPIClient()
    private lateinit var db: WeatherDataBase
    private val dm: DataBaseManager = DataBaseManager()
    private lateinit var dao: WeatherDao
    private val cxt = context
    private val scp = scope
    suspend fun getWeatherData(): DataManagerAPIClient.WeatherData {
        return getWeatherDataFromApi() ?:  //if I exceeded my 50 calls per day the Weather data from Api will be null
        return getWeatherDataFromDB()      //then return the Weather data from a data base
    }

    private suspend fun getWeatherDataFromApi(): DataManagerAPIClient.WeatherData? =
        api.getAllWeatherData()

    private suspend fun getWeatherDataFromDB(): DataManagerAPIClient.WeatherData {
        db = WeatherDataBase.getDatabase(cxt, scp)
        dao = db.getWeatherDao()
        if (dm.getFromDB(dao).isDayTime == null)
            dm.setToDB(dao, FixedData.fixedData)
        return dm.getFromDB(dao)

    }


}