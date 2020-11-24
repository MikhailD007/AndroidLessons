package org.vimteam.lesson1

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.ConfigurationCompat
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import org.vimteam.lesson1.R.layout.activity_main
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : AppCompatActivity() {

    val LOG_TAG = "L1"

    private var autocompleteFragment: AutocompleteSupportFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        val crashlitics = FirebaseCrashlytics.getInstance()
        with(crashlitics) {
            setUserId("")
            setCustomKey("UserName", "Spectator")
            setCustomKey("UserPhone", "")
        }

        if (!Places.isInitialized()) Places.initialize(this, getString(R.string.google_places_key))
        autocompleteFragment = childFragmentManager.findFragmentById(R.id.cityAutocompleteFragment) as AutocompleteSupportFragment?
        autocompleteFragment?.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG))
        autocompleteFragment?.setHint(getString(R.string.city))
        autocompleteFragment?.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) { presenter.onPlaceSelected(place, ConfigurationCompat.getLocales(
                Resources.getSystem().configuration)[0].country) }
            override fun onError(status: Status) {
                showError(status.toString())
                Log.e(LOG_TAG, "An error occurred while selecting Place: $status")
            }
        })
        clearCityButton.setOnClickListener {
            presenter.onPlaceSelected()
            autocompleteFragment?.setText("")
        }
    }

    fun showError(message: String) {
        Log.e(LOG_TAG,"Registration error: $message")
        lockButtons(false)
        this.hideKeyboard()
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }
}