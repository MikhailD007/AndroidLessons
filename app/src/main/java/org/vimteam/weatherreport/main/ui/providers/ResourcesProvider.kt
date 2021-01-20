package org.vimteam.weatherreport.main.ui.providers

import android.content.Context
import org.vimteam.weatherreport.main.domain.contracts.ResourcesProviderContract

class ResourcesProvider(private val context: Context) : ResourcesProviderContract{

    override fun getString(id: Int) = context.getString(id)

    override fun getString(stringName: String) = context.getString(context.resources.getIdentifier(stringName, "string", context.packageName))

}