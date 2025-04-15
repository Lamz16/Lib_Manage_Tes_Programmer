package com.lamz.libmanage.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.lamz.libmanage.data.LibManRepository
import com.lamz.libmanage.data.pref.UserPreference
import com.lamz.libmanage.data.room.LibManDatabase
import com.lamz.libmanage.presentation.ui.home.HomeViewModel
import com.lamz.libmanage.presentation.ui.history.HistoryViewModel
import com.lamz.libmanage.presentation.viewmodel.LoginViewModel
import com.lamz.libmanage.presentation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

val appModule = module {

    single<DataStore<Preferences>> {
        androidContext().dataStore
    }

    single {
        UserPreference(get())
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}

val databaseModule = module {
    factory { get<LibManDatabase>().libManDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            LibManDatabase::class.java,
            "libman.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { UserPreference(get()) }
    single { LibManRepository(get(), get()) }
}