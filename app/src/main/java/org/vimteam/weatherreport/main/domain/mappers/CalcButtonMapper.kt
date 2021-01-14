package org.vimteam.weatherreport.main.domain.mappers

enum class CalcButtonMapper(val value: Int) {
    CLEAR(0),
    BACKSPACE(1),
    PLUS(15),
    MINUS(11),
    MULTIPLY(7),
    DIVIDE(3),
    SQRT(2),
    CHANGE_SIGNATURE(16),
    EQUAL(19),
    DECIMAL_DOT(18),
    DIGIT(999);

    companion object {
        fun valueOf(value: Int): CalcButtonMapper? =
            values().find { it.value == value } ?: values().find { it.value == 999 }

        fun getDigitValue(position: Int): Int = when (position) {
            4, 5, 6 -> position + 3
            8, 9, 10 -> position - 4
            12, 13, 14 -> position - 11
            else -> 0
        }
    }
}