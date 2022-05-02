package com.anabelmm.myweatherapp.repository_model

import retrofit2.Response

class DataManagerAPIClient {
    lateinit var responseCurrentCond: Response<List<CurrentConditionData>>
    lateinit var responseForecast5D: Response<Forecast5DData>
    lateinit var responseForecast12H: Response<List<Forecast12HData>>

    data class WeatherData(
        var observationDateTime: String?,
        var currWeatherPhrase: String?,
        var isDayTime: Boolean?,
        var currTemperature: Double?,
        var realFeelTemperature: Double?,
        var list5DaysWeather: List<Each5Days>,
        var list12HWeather: List<Each12H>
    )

    data class Each5Days(
        var minValue: Double?,
        var maxValue: Double?,
        var iconDay: Int?,
        var dayPhrase: String?,
        var iconNight: Int?,
        var nightPhrase: String?
    )

    data class Each12H(
        var epochDateTime: Long?,
        var weatherIcon: Int?,
        var hourlyTempValue: Double?
    )

    suspend fun getAllWeatherData(): WeatherData? {
        responseCurrentCond = APICurrentCondition.retrofitServiceCurrCond.getCurrentCondition()
        responseForecast5D = APIForecast5D.retrofitServiceForecast5D.getForecast5D()
        responseForecast12H = APIForecast12H.retrofitServiceForecast12H.getForecast12H()

        //isSuccessful() returns true if code() is in the range [200..300):
        if (!responseCurrentCond.isSuccessful || !responseForecast5D.isSuccessful || !responseForecast12H.isSuccessful) 
            return null

        val listD = mutableListOf<Each5Days>()
        for (i in 0..4) {
            val minV = responseForecast5D.body()?.list?.get(i)?.temperature?.minimum?.minValue
            val maxV = responseForecast5D.body()?.list?.get(i)?.temperature?.maximum?.maxValue
            val icDay = responseForecast5D.body()?.list?.get(i)?.day?.iconDay
            val dPhrase = responseForecast5D.body()?.list?.get(i)?.day?.dayPhrase
            val iNight = responseForecast5D.body()?.list?.get(i)?.night?.iconNight
            val nPhrase = responseForecast5D.body()?.list?.get(i)?.night?.nightPhrase
            listD.add(i, (Each5Days(minV, maxV, icDay, dPhrase, iNight, nPhrase)))
        }
        val listH = mutableListOf<Each12H>()
        for (i in 0..11) {
            val epDateTime = responseForecast12H.body()?.get(i)?.epochDateTime
            val wIcon = responseForecast12H.body()?.get(i)?.weatherIcon
            val hTempValue = responseForecast12H.body()?.get(i)?.hourlyTemperature?.hourlyTempValue
            listH.add(i, Each12H(epDateTime, wIcon, hTempValue))
        }

        return WeatherData(
            observationDateTime = responseCurrentCond.body()?.get(0)?.localObservationDateTime,
            currWeatherPhrase = responseCurrentCond.body()?.get(0)?.weatherPhrase,
            isDayTime = responseCurrentCond.body()?.get(0)?.isDayTime,
            currTemperature = responseCurrentCond.body()
                ?.get(0)?.temperature?.tempMetric?.tempValueDouble,
            realFeelTemperature = responseCurrentCond.body()
                ?.get(0)?.realFeelTemperature?.tempRealFeelMetric?.tempRealFeelValueDouble,
            list5DaysWeather = listD,
            list12HWeather = listH
        )
    }
}