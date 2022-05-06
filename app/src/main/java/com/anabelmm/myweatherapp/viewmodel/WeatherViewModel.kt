package com.anabelmm.myweatherapp.viewmodel
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anabelmm.myweatherapp.databinding.ActivityMainBinding
import com.anabelmm.myweatherapp.repository_model.DataManagerAPIClient
import com.anabelmm.myweatherapp.repository_model.WeatherDataRepository
import com.anabelmm.myweatherapp.repository_model.db.FixedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class WeatherViewModel(context: Context, scope: CoroutineScope): ViewModel() {
    private val repository: WeatherDataRepository = WeatherDataRepository(context, scope)
    val weatherModel = MutableLiveData<DataManagerAPIClient.WeatherData>()
    fun onCreate() {
        viewModelScope.launch {
            val result = repository.getWeatherData()
            weatherModel.postValue(result)
        }
    }
}