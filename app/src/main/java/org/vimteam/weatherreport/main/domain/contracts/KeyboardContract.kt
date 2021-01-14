package org.vimteam.weatherreport.main.domain.contracts

import org.vimteam.weatherreport.main.domain.mappers.CalcButtonMapper

interface KeyboardContract {
    fun onKeyPressed(button: CalcButtonMapper, digit: Int)
}