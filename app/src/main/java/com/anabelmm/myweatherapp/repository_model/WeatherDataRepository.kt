package com.anabelmm.myweatherapp.repository_model

import android.content.Context
import android.util.Log
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
        val dataDB = getWeatherDataFromDB()
        val currTime = System.currentTimeMillis()
        val prevTime = dataDB.list12HWeather[0].epochDateTime!!
        val deltaTime =
            (currTime - prevTime)/60000   // The elapsed time converted to min, between current time and previously time of call to server
        return if (deltaTime > 60) {        // If more than 60 min has elapsed, it makes a new call to server, otherwise get data from Data Base
            val dataApi = getWeatherDataFromApi()
            if (dataApi == null) {        //if I exceeded my 50 calls per day or there is a problem connecting with the service,
                dataDB                         // the Weather data from Api will be null, then return the Weather data from the data base
            } else {
                dm.updateDataBase(dao, dataApi)
                dataApi
            }
        } else
            dataDB
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