package org.vimteam.lesson1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import org.vimteam.lesson1.R.layout.activity_main
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG = "L1"

    private var autocompleteFragment: AutocompleteSupportFragment? = null
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val crashlitics = FirebaseCrashlytics.getInstance()
        with(crashlitics) {
            setUserId("")
            setCustomKey("UserName", "Spectator")
            setCustomKey("UserPhone", "")
        }

        initDateTimePicker(getString(R.string.select_date))
        selectDateEditText.setOnFocusChangeListener { _, b -> if (b) datePickerDialog.show(supportFragmentManager, "DatePickerDialog") }
        selectDateEditText.setOnClickListener { datePickerDialog.show(supportFragmentManager, "DatePickerDialog")}

        initDayNight(0)

        if (!Places.isInitialized()) Places.initialize(this, getString(R.string.google_places_key))
        autocompleteFragment = supportFragmentManager.findFragmentById(R.id.cityAutocompleteFragment) as AutocompleteSupportFragment?
        autocompleteFragment?.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG))
        autocompleteFragment?.setHint(getString(R.string.city))
        autocompleteFragment?.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {  }
            override fun onError(status: Status) {
                showError(status.toString())
                Log.e(LOG_TAG, "An error occurred while selecting Place: $status")
            }
        })
        clearCityButton.setOnClickListener {
            autocompleteFragment?.setText("")
        }
    }

    fun initDayNight(position: Int) {
        dayNightTextView.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.day_night)))
        dayNightTextView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            dayNightTextView.setText(resources.getStringArray(R.array.day_night)[position], false)
        }
        dayNightTextView.setText(resources.getStringArray(R.array.day_night)[position], false)
    }

    fun showError(message: String) {
        Log.e(LOG_TAG,"Error: $message")
        this.hideKeyboard()
        Snackbar.make(findViewById(R.id.contentView), message, Snackbar.LENGTH_LONG).show()
    }

    fun initDateTimePicker(title: String, isThemeDark: Boolean = false) {
        datePickerDialog = DatePickerDialog.newInstance { _, year, monthOfYear, dayOfMonth ->
            selectDateEditText.setText(
                localeDateToString(
                    LocalDate(
                        year,
                        monthOfYear + 1,
                        dayOfMonth
                    )
                )
            )
            selectDateTextInputLayout.error = null
        }
        datePickerDialog.isThemeDark = isThemeDark
        datePickerDialog.showYearPickerFirst(false)
        datePickerDialog.setTitle(title)
    }

    fun Activity.hideKeyboard() {
        val view = currentFocus ?: View(this)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Activity.showKeyboard(view: View) {
        val view = currentFocus ?: View(this)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun localeDateToString(date: LocalDate, locale: Locale = Locale.getDefault()): String {
        return date.toString(DateTimeFormat.shortDate().withLocale(locale))
    }
}