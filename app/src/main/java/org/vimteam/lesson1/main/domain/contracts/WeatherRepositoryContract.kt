package org.vimteam.lesson1.main.domain.contracts

import org.vimteam.lesson1.main.domain.models.WeatherData

interface WeatherRepositoryContract {

    fun getStoredData(): WeatherData
}