package org.vimteam.weatherreport.main.domain.contracts

import org.vimteam.weatherreport.main.domain.models.CalcActions
import org.vimteam.weatherreport.main.domain.models.CalcData

interface CalcRepositoryContract {

    fun getCalcData(): CalcData

    fun setFirstArgument(value: String)
    fun setSecondArgument(value: String)
    fun setAction(value: CalcActions?)

    fun clear()
}