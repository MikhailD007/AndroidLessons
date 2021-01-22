package org.vimteam.weatherreport.main.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_calc.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.weatherreport.R
import org.vimteam.weatherreport.main.base.setThemeFromPreferences
import org.vimteam.weatherreport.main.domain.mappers.CalcButtonMapper
import org.vimteam.weatherreport.main.domain.mappers.CalcButtonMapper.*
import org.vimteam.weatherreport.main.domain.contracts.CalcContract
import org.vimteam.weatherreport.main.domain.contracts.KeyboardContract
import org.vimteam.weatherreport.main.domain.models.CalcActions
import org.vimteam.weatherreport.main.ui.fragments.KeyboardFragment

class CalcActivity : AppCompatActivity(), KeyboardContract {

    private val vm: CalcContract.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setThemeFromPreferences()
        setContentView(R.layout.activity_calc)
        initView()

    }

    override fun onKeyPressed(button: CalcButtonMapper, digit: Int) {
        //Snackbar.make(calcConstraintLayout, keyPosition.toString(), Snackbar.LENGTH_LONG).show()
        when (button) {
            CLEAR -> vm.clear()
            BACKSPACE -> vm.removeSymbol()
            SQRT -> vm.setAction(CalcActions.SQRT)
            DIVIDE -> vm.setAction(CalcActions.DIVIDE)
            MULTIPLY -> vm.setAction(CalcActions.MULTIPLY)
            MINUS -> vm.setAction(CalcActions.MINUS)
            PLUS -> vm.setAction(CalcActions.PLUS)
            CHANGE_SIGNATURE -> vm.changeSignature()
            DECIMAL_DOT -> vm.addDecimalDot()
            EQUAL -> {
                try {
                    vm.evaluateExpression()
                } catch (e: Exception) {
                    Snackbar.make(calcConstraintLayout, e.localizedMessage ?:e.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
            DIGIT -> vm.addDigit(digit)
        }
    }

    private fun initView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.keyboardFrame, KeyboardFragment())
            .commit()
        vm.ioData.observe(this) { ioTextView.text = it }
    }
}