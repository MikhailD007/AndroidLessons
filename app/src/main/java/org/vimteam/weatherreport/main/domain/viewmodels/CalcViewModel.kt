package org.vimteam.weatherreport.main.domain.viewmodels

import androidx.lifecycle.MutableLiveData
import org.vimteam.weatherreport.main.domain.contracts.CalcContract
import org.vimteam.weatherreport.main.domain.contracts.CalcRepositoryContract
import org.vimteam.weatherreport.main.domain.contracts.ResourcesProviderContract
import org.vimteam.weatherreport.main.domain.models.CalcActions

class CalcViewModel(
    private val repo: CalcRepositoryContract,
    private val res: ResourcesProviderContract
) : CalcContract.ViewModel() {

    override val ioData = MutableLiveData<String>()

    init {
        ioData.value = ""
    }

    override fun addDigit(digit: Int) {
        changeArgument {
            if (it == "0" || it == "-0") null
            else "$it$digit"
        }
    }

    override fun addDecimalDot() {
        changeArgument {
            when {
                it.contains(".") -> null
                it.isEmpty() -> "0."
                else -> "$it."
            }
        }
    }

    override fun changeSignature() {
        changeArgument {
            when {
                it.isEmpty() -> null
                it.startsWith("-") -> it.substring(1, it.length)
                else -> "-$it"
            }
        }
    }

    override fun removeSymbol() {
        val cd = repo.getCalcData()
        when {
            cd.secondArgument != "" -> {
                var argument = cd.secondArgument
                if (argument.isNotEmpty()) argument = argument.substring(0, argument.length - 1)
                if (argument == "-") argument = ""
                repo.setSecondArgument(argument)
                ioData.value = formatIOString(cd.firstArgument, argument, cd.action)
            }
            cd.action != null -> {
                repo.setAction(null)
                ioData.value = formatIOString(cd.firstArgument, cd.secondArgument, null)
            }
            cd.firstArgument != "" -> {
                var argument = cd.firstArgument
                if (argument.isNotEmpty()) argument = argument.substring(0, argument.length - 1)
                if (argument == "-") argument = ""
                repo.setFirstArgument(argument)
                ioData.value = formatIOString(argument, cd.secondArgument, cd.action)
            }
        }

    }

    override fun clear() {
        repo.clear()
        ioData.value = formatIOString("", "", null)
    }

    override fun setAction(action: CalcActions) {
        val cd = repo.getCalcData()
        if (cd.firstArgument.isEmpty()) return
        repo.setAction(action)
        ioData.value = formatIOString(cd.firstArgument, cd.secondArgument, action)
    }

    override fun evaluateExpression() {
        val cd = repo.getCalcData()
        with(cd) {
            if (firstArgument == "") throw Exception(res.getString("incorrect_input_first_argument"))
            if (action == null) throw Exception(res.getString("no_action_specified"))
            if (secondArgument == "") throw Exception(res.getString("incorrect_input_second_argument"))
            val mFirstArgument =
                if (abs(firstArgument) == res.getString("infinity_symbol")) res.getString("infinity") else firstArgument
            var firstArgumentDouble: Double = 0.0
            var secondArgumentDouble: Double = 0.0
            try {
                firstArgumentDouble = mFirstArgument.toDouble()
            } catch (e: NumberFormatException) {
                throw Exception(res.getString("incorrect_input_first_argument"))
            } catch (e: Exception) {
                throw e
            }
            try {
                secondArgumentDouble = secondArgument.toDouble()
            } catch (e: NumberFormatException) {
                throw Exception(res.getString("incorrect_input_second_argument"))
            } catch (e: Exception) {
                throw e
            }
            val result = when (action) {
                CalcActions.PLUS -> firstArgumentDouble + secondArgumentDouble
                CalcActions.MINUS -> firstArgumentDouble - secondArgumentDouble
                CalcActions.MULTIPLY -> firstArgumentDouble * secondArgumentDouble
                CalcActions.DIVIDE -> firstArgumentDouble / secondArgumentDouble
                else -> throw Exception(res.getString("unknown_action"))
            }
            var strResult = result.toString()
            if (strResult.equals(
                    res.getString("nan"),
                    ignoreCase = true
                )
            ) throw Exception(res.getString("incorrect_operation"))
            if (abs(strResult).equals(
                    res.getString("infinity"),
                    ignoreCase = true
                )
            ) strResult = res.getString("infinity_symbol")
            if (strResult.endsWith(".0")) strResult = strResult.substring(0, strResult.length - 2)
            repo.clear()
            repo.setFirstArgument(strResult)
            ioData.value = formatIOString(strResult, "", null)
        }
    }

    //---------------------------------------------------------------------------------------------

    private fun formatIOString(
        firstArgument: String,
        secondArgument: String,
        action: CalcActions?
    ): String {
        val mSecondArgument = if (secondArgument.isEmpty()) "" else "\n${secondArgument}"
        val mAction = if (action == null) "" else "\n${action.symbol}"
        return "$firstArgument$mAction$mSecondArgument"
    }

    private fun changeArgument(block: (value: String) -> String?) {
        val cd = repo.getCalcData()
        if (cd.action == null) {
            val argument = block(cd.firstArgument) ?: return
            repo.setFirstArgument(argument)
            ioData.value = formatIOString(argument, cd.secondArgument, cd.action)
        } else {
            val argument = block(cd.secondArgument) ?: return
            repo.setSecondArgument(argument)
            ioData.value = formatIOString(cd.firstArgument, argument, cd.action)
        }
    }

    private fun abs(value: String): String =
        if (value.startsWith("-")) value.substring(1, value.length) else value

}