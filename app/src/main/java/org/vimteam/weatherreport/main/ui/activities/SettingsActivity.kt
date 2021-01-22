package org.vimteam.weatherreport.main.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.vimteam.weatherreport.R
import org.vimteam.weatherreport.main.ui.fragments.SettingsFragment

class SettingsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }

}