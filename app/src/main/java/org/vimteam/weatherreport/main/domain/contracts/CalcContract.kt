package org.vimteam.weatherreport.main.domain.contracts

import androidx.lifecycle.LiveData
import org.vimteam.weatherreport.main.domain.models.CalcActions

interface CalcContract {
    abstract class ViewModel : androidx.lifecycle.ViewModel() {
        abstract val ioData: LiveData<String>

        abstract fun addDigit(digit: Int)
        abstract fun addDecimalDot()
        abstract fun changeSignature()

        abstract fun removeSymbol()
        abstract fun clear()
        abstract fun setAction(action: CalcActions)

        @Throws(Exception::class)
        abstract fun evaluateExpression()
    }
}