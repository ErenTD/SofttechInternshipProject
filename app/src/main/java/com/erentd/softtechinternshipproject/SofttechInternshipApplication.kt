package com.erentd.softtechinternshipproject

import android.app.Application
import com.erentd.softtechinternshipproject.koinmodules.networkModule
import com.erentd.softtechinternshipproject.koinmodules.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SofttechInternshipApplication : Application() {
    private val koinModules = listOf(networkModule, repositoryModule)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SofttechInternshipApplication)
            koin.loadModules(koinModules)
        }
    }
}