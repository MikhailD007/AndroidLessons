package org.vimteam.weatherreport.main.domain.contracts

import org.vimteam.weatherreport.main.domain.models.WeatherData

interface WeatherRepositoryContract {

    fun getStoredData(): WeatherData
}