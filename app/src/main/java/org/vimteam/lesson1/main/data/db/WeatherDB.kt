package org.vimteam.lesson1.main.data.db

import org.vimteam.lesson1.main.domain.models.WeatherData
import org.vimteam.lesson1.main.domain.models.Wind
import org.vimteam.lesson1.main.domain.models.WindDirection

class WeatherDB {

    fun getStoredData(): WeatherData {
        //todo get data from DB using Room
        return WeatherData("Saratov",-5.3f, Wind(5,WindDirection.NORTH))
    }

}