package org.vimteam.weatherreport.main.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.weatherreport.main.domain.contracts.MainContract
import org.vimteam.weatherreport.main.domain.contracts.WeatherRepositoryContract
import org.vimteam.weatherreport.main.domain.models.WeatherData

class MainViewModel(
        private val repo: WeatherRepositoryContract
): MainContract.ViewModel() {

    override val weatherData = MutableLiveData<WeatherData>()

    init {
        //при первом создании класса
        //coroutines
        weatherData.value = repo.getStoredData()

    }

}