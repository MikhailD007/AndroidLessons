package org.vimteam.weatherreport.main.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import org.vimteam.weatherreport.R
import org.vimteam.weatherreport.main.base.setThemeFromPreferences

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        findPreference<SwitchPreferenceCompat>("darkThemeSelector")?.setOnPreferenceClickListener {
            activity?.setThemeFromPreferences()
            activity?.recreate()
//            if ((it as SwitchPreferenceCompat).isChecked)
//                Log.d(LOG_TAG, "Dark is ON")
//            else
//                Log.d(LOG_TAG, "Dark is OFF")
            true
        }
    }
}
