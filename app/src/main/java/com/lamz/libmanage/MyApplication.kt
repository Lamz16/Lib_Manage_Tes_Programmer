package com.lamz.libmanage

import android.app.Application
import com.lamz.libmanage.di.appModule
import com.lamz.libmanage.di.databaseModule
import com.lamz.libmanage.di.repositoryModule
import com.lamz.libmanage.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    appModule,
                    viewModelModule
                )
            )
        }
    }
}