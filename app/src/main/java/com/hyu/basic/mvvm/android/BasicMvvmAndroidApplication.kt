package com.hyu.basic.mvvm.android

import android.app.Application
import com.hyu.basic.mvvm.android.di.amiiboModule
import com.hyu.basic.mvvm.android.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WebDataViewApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            //            androidLogger(Level.DEBUG)
            androidContext(this@WebDataViewApplication)
            modules( amiiboModule, dataModule)
        }
    }
}

