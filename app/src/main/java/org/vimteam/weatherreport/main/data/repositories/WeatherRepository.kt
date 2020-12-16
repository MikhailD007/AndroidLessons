package org.vimteam.weatherreport.main.data.repositories

import org.vimteam.weatherreport.main.data.db.WeatherDB
import org.vimteam.weatherreport.main.domain.contracts.WeatherRepositoryContract
import org.vimteam.weatherreport.main.domain.models.WeatherData

class WeatherRepository(
        private val db: WeatherDB
): WeatherRepositoryContract {

    override fun getStoredData(): WeatherData {
        return db.getStoredData()
    }

}