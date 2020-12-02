package org.vimteam.lesson1.main.data.repositories

import org.vimteam.lesson1.main.data.db.WeatherDB
import org.vimteam.lesson1.main.domain.contracts.WeatherRepositoryContract
import org.vimteam.lesson1.main.domain.models.WeatherData

class WeatherRepository(
        private val db: WeatherDB
): WeatherRepositoryContract {

    override fun getStoredData(): WeatherData {
        return db.getStoredData()
    }

}