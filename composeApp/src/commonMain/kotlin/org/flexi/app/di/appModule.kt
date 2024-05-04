package org.flexi.app.di

import org.flexi.app.domain.repository.Repository
import org.flexi.app.presentation.viewmodels.MainViewModel
import org.koin.dsl.module

val appModule = module {
    single { Repository() }
    single {
        MainViewModel(get())
    }
}