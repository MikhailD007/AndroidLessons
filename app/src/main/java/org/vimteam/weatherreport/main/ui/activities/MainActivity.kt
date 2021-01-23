package org.vimteam.weatherreport.main.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.vimteam.weatherreport.R
import org.vimteam.weatherreport.R.layout.activity_main
import org.vimteam.weatherreport.main.base.MainConstants.LOG_TAG
import org.vimteam.weatherreport.main.base.hideKeyboard
import org.vimteam.weatherreport.main.base.setThemeFromPreferences
import org.vimteam.weatherreport.main.domain.contracts.MainContract
import java.util.*

class MainActivity : AppCompatActivity() {

    private val vm: MainContract.ViewModel by viewModel()

    private var autocompleteFragment: AutocompleteSupportFragment? = null
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setThemeFromPreferences()
        setContentView(activity_main)
        //vm = ViewModelProvider(this)[MainViewModel::class.java]

        val crashlitics = FirebaseCrashlytics.getInstance()
        with(crashlitics) {
            setUserId("")
            setCustomKey("UserName", "Spectator")
            setCustomKey("UserPhone", "")
        }

        vm.weatherData.observe(this) {
            autocompleteFragment?.setText(it.city)
        }

        //------------------------------------------------------------------------------------

        initDateTimePicker(getString(R.string.select_date))
        periodEditText.setOnFocusChangeListener { _, b ->
            if (b) datePickerDialog.show(
                supportFragmentManager,
                "DatePickerDialog"
            )
        }
        periodEditText.setOnClickListener {
            datePickerDialog.show(
                supportFragmentManager,
                "DatePickerDialog"
            )
        }

        if (!Places.isInitialized()) Places.initialize(this, getString(R.string.google_places_key))
        autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.cityAutocompleteFragment) as AutocompleteSupportFragment?
        autocompleteFragment?.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment?.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.ADDRESS_COMPONENTS,
                Place.Field.LAT_LNG
            )
        )
        autocompleteFragment?.setHint(getString(R.string.city))
        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {}
            override fun onError(status: Status) {
                if (status.isCanceled) return
                showError(status.toString())
                Log.e(LOG_TAG, "An error occurred while selecting Place: $status")
            }
        })
        clearCityButton.setOnClickListener {
            autocompleteFragment?.setText("")
        }
        openCalcButton.setOnClickListener {
            startActivity(Intent(this, CalcActivity::class.java))
        }
        val settingsActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            recreate()
        }
        settingsButton.setOnClickListener {
            settingsActivityResult.launch(
                Intent(this, SettingsActivity::class.java)
            )
        }
    }

    fun showError(message: String) {
        Log.e(LOG_TAG, "Error: $message")
        this.hideKeyboard()
        Snackbar.make(findViewById(R.id.contentView), message, Snackbar.LENGTH_LONG).show()
    }

    private fun initDateTimePicker(title: String, isThemeDark: Boolean = false) {
        datePickerDialog = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            periodEditText.setText(
                localeDateToString(
                    LocalDate(
                        year,
                        monthOfYear + 1,
                        dayOfMonth
                    )
                )
            )
            periodTextInputLayout.error = null
        }
        datePickerDialog.isThemeDark = isThemeDark
        datePickerDialog.showYearPickerFirst(false)
        datePickerDialog.setTitle(title)
    }


    private fun localeDateToString(date: LocalDate, locale: Locale = Locale.getDefault()): String {
        return date.toString(DateTimeFormat.shortDate().withLocale(locale))
    }
}