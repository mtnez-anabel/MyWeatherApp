package com.anabelmm.myweatherapp.repository_model.db

import com.anabelmm.myweatherapp.repository_model.DataManagerAPIClient

class FixedData {
    companion object {
        val fixedData = DataManagerAPIClient.WeatherData(
            currWeatherPhrase = "Sunny",
            isDayTime = true,
            currTemperature = 18.5,
            realFeelTemperature = 17.4,
            list5DaysWeather = listOf(
                DataManagerAPIClient.Each5Days(
                    minValue = 7.3,
                    maxValue = 21.9,
                    iconDay = 1,
                    dayPhrase = "Sunny",
                    iconNight = 33,
                    nightPhrase = "Clear"
                ), DataManagerAPIClient.Each5Days(
                    minValue = 7.7,
                    maxValue = 20.3,
                    iconDay = 1,
                    dayPhrase = "Sunny",
                    iconNight = 33,
                    nightPhrase = "Clear"
                ), DataManagerAPIClient.Each5Days(
                    minValue = 8.1,
                    maxValue = 20.2,
                    iconDay = 2,
                    dayPhrase = "Mostly sunny",
                    iconNight = 33,
                    nightPhrase = "Clear"
                ), DataManagerAPIClient.Each5Days(
                    minValue = 8.8,
                    maxValue = 19.5,
                    iconDay = 4,
                    dayPhrase = "Intermittent clouds",
                    iconNight = 34,
                    nightPhrase = "Mostly clear"
                ), DataManagerAPIClient.Each5Days(
                    minValue = 9.0,
                    maxValue = 20.2,
                    iconDay = 3,
                    dayPhrase = "Partly sunny",
                    iconNight = 34,
                    nightPhrase = "Mostly clear"
                )
            ),
            list12HWeather = listOf(
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648144800,
                    weatherIcon = 33,
                    hourlyTempValue = 16.2
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648148400,
                    weatherIcon = 33,
                    hourlyTempValue = 14.0
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648152000,
                    weatherIcon = 33,
                    hourlyTempValue = 12.5
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648155600,
                    weatherIcon = 33,
                    hourlyTempValue = 10.9
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648159200,
                    weatherIcon = 33,
                    hourlyTempValue = 10.2
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648162800,
                    weatherIcon = 33,
                    hourlyTempValue = 9.4
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648166400,
                    weatherIcon = 33,
                    hourlyTempValue = 8.9
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648170000,
                    weatherIcon = 33,
                    hourlyTempValue = 8.6
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648173600,
                    weatherIcon = 33,
                    hourlyTempValue = 8.3
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648177200,
                    weatherIcon = 33,
                    hourlyTempValue = 7.9
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648180800,
                    weatherIcon = 33,
                    hourlyTempValue = 7.6
                ),
                DataManagerAPIClient.Each12H(
                    epochDateTime = 1648184400,
                    weatherIcon = 33,
                    hourlyTempValue = 7.3
                )
            )
        )
    }
}