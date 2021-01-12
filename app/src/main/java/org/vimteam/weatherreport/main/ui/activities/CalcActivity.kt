package org.vimteam.weatherreport.main.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_calc.*
import org.vimteam.weatherreport.R
import org.vimteam.weatherreport.main.domain.contracts.KeyboardContract
import org.vimteam.weatherreport.main.ui.fragments.KeyboardFragment

class CalcActivity : AppCompatActivity(), KeyboardContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)
        val fragment = KeyboardFragment()
        fragment.arguments = Bundle()
        supportFragmentManager.beginTransaction()
            .add(R.id.keyboardFrame, fragment)
            .commit()
    }

    override fun onKeyPressed(keyPosition: Int) {
        //Snackbar.make(calcConstraintLayout, keyPosition.toString(), Snackbar.LENGTH_LONG).show()
        with(ioTextView) {
            when (keyPosition) {
                0 -> text = ""
                1 -> {
                    if (text.isNotEmpty()) text = text.substring(0, text.length - 1)
                    if (text == "-") text = ""
                }
                2 -> text = text //TODO sqrt operation
                3 -> text = text //TODO divide operation
                7 -> text = text //TODO multiply operation
                11 -> text = text //TODO minus operation
                15 -> text = text //TODO plus operation
                16 -> //change signature operation
                    if (text!="") text = if (text.startsWith("-")) text.substring(1,text.length) else "-${text}"
                18 -> if (!text.contains(".")) {
                    text = if (text == "") "${text}0." else "$text."
                }
                19 -> text = text //TODO equals operation
                4,5,6,8,9,10,12,13,14,17 ->{
                    if (text == "0" || text == "-0") return
                    val digit = when (keyPosition) {
                        4,5,6 -> keyPosition + 3
                        8,9,10 -> keyPosition - 4
                        12,13,14 -> keyPosition - 11
                        else -> 0
                    }
                    text = "$text$digit"
                }
            }
        }
    }
}