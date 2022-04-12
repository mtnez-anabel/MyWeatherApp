package com.anabelmm.myweatherapp.repository_model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val key = "C1uAGGDch94QSJ6FSiv5X8J72mwv1cp9"
private const val myLocationKey = "2579865"
const val BASE_ULR =
    "https://dataservice.accuweather.com/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_ULR)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface CurrCondAPIService {
    @GET("currentconditions/v1/$myLocationKey?apikey=$key&details=true")
    suspend fun getCurrentCondition(): Response<List<CurrentConditionData>>
}

object APICurrentCondition {
    val retrofitServiceCurrCond: CurrCondAPIService by lazy {
        retrofit.create(
            CurrCondAPIService::class.java
        )
    }
}

interface Forecast5DAPIService {
    @GET("forecasts/v1/daily/5day/$myLocationKey?apikey=$key&metric=true")
    suspend fun getForecast5D(): Response<Forecast5DData>
}

object APIForecast5D {
    val retrofitServiceForecast5D: Forecast5DAPIService by lazy {
        retrofit.create(
            Forecast5DAPIService::class.java
        )
    }
}

interface Forecast12HAPIService {
    @GET("forecasts/v1/hourly/12hour/$myLocationKey?apikey=$key&metric=true")
    suspend fun getForecast12H(): Response<List<Forecast12HData>>
}

object APIForecast12H {
    val retrofitServiceForecast12H: Forecast12HAPIService by lazy {
        retrofit.create(
            Forecast12HAPIService::class.java
        )
    }
}