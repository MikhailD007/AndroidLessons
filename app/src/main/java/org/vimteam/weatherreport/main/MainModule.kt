package org.vimteam.weatherreport.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy
import org.vimteam.weatherreport.main.data.db.WeatherDB
import org.vimteam.weatherreport.main.data.repositories.WeatherRepository
import org.vimteam.weatherreport.main.domain.contracts.MainContract
import org.vimteam.weatherreport.main.domain.contracts.WeatherRepositoryContract
import org.vimteam.weatherreport.main.domain.viewmodels.MainViewModel

object MainModule {
    fun get() = module {
        factory { WeatherDB() }
        factoryBy<WeatherRepositoryContract, WeatherRepository>()
        viewModel<MainContract.ViewModel> { MainViewModel(get()) }
    }
}