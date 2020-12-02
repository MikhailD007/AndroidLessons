package org.vimteam.lesson1.main.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.lesson1.main.domain.contracts.MainContract
import org.vimteam.lesson1.main.domain.contracts.WeatherRepositoryContract
import org.vimteam.lesson1.main.domain.models.WeatherData

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