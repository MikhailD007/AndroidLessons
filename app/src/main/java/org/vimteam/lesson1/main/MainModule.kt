package org.vimteam.lesson1.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy
import org.vimteam.lesson1.main.data.db.WeatherDB
import org.vimteam.lesson1.main.data.repositories.WeatherRepository
import org.vimteam.lesson1.main.domain.contracts.MainContract
import org.vimteam.lesson1.main.domain.contracts.WeatherRepositoryContract
import org.vimteam.lesson1.main.domain.viewmodels.MainViewModel

object MainModule {
    fun get() = module {
        factory { WeatherDB() }
        factoryBy<WeatherRepositoryContract, WeatherRepository>()
        viewModel<MainContract.ViewModel> { MainViewModel(get()) }
    }
}