package org.vimteam.weatherreport.main.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.weatherreport.main.domain.models.WeatherData

interface MainContract {

    abstract class ViewModel: androidx.lifecycle.ViewModel() {
        abstract val weatherData: LiveData<WeatherData>
    }
}