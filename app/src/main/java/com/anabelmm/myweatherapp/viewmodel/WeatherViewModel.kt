package com.anabelmm.myweatherapp.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anabelmm.myweatherapp.repository_model.DataManagerAPIClient
import com.anabelmm.myweatherapp.repository_model.WeatherDataRepository
import kotlinx.coroutines.launch

class WeatherViewModel(): ViewModel() {
    private val repository: WeatherDataRepository = WeatherDataRepository()
    val weatherModel = MutableLiveData<DataManagerAPIClient.WeatherData>()
    fun onCreate() {
        viewModelScope.launch {
            val result = repository.getWeatherDataFromApi()
            weatherModel.postValue(result)
        }
    }
}