package org.vimteam.weatherreport.main.domain.models

data class CalcData(
    val firstArgument: String = "",
    val secondArgument: String = "",
    val action: CalcActions? = null
)