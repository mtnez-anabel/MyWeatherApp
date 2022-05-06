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
    private val db: WeatherDataBase = WeatherDataBase.getDatabase(context, scope)
    private val dm: DataBaseManager = DataBaseManager()
    private val dao: WeatherDao = db.getWeatherDao()

    suspend fun getWeatherData(): DataManagerAPIClient.WeatherData {
        if(dao.getWeatherData() == null){
            dm.setToDB(dao, FixedData.fixedData)
        }
        val dataDB = getWeatherDataFromDB()
        val currTime = System.currentTimeMillis()
        val prevTime = dataDB.list12HWeather[0].epochDateTime!! //It's in seconds
        val deltaTime =               // The elapsed time in min, between current time and previously time of call to server, prevTime must to be converted
            (currTime/1000 - prevTime)/60  // from sec to min, while currTime from millisecond to min
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

    private suspend fun getWeatherDataFromDB(): DataManagerAPIClient.WeatherData =
        dm.getFromDB(dao)
}