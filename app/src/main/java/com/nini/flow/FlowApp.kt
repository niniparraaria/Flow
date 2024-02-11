package com.nini.flow

import android.app.Application
import com.nini.flow.core.modules.networkModule
import com.nini.flow.core.modules.remoteDataSourceModule
import com.nini.flow.core.modules.repositoryModule
import com.nini.flow.core.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FlowApp: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FlowApp)
            androidLogger()
            modules(networkModule, remoteDataSourceModule, repositoryModule, viewModelModule)
        }
    }
}