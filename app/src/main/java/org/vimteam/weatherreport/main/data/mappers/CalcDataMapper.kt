package org.vimteam.weatherreport.main.data.mappers

import org.vimteam.weatherreport.main.data.models.CalcDataDB
import org.vimteam.weatherreport.main.domain.models.CalcActions
import org.vimteam.weatherreport.main.domain.models.CalcData

object CalcDataMapper {
    fun map(value: CalcDataDB) = with(value) {
        CalcData(firstArgument, secondArgument, action?.let { CalcActions.values()[it] })
    }
}