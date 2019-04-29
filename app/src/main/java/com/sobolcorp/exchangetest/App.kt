package com.sobolcorp.exchangetest

import android.app.Application
import com.sobolcorp.exchangetest.rx.AppComponent
import com.sobolcorp.exchangetest.rx.DaggerAppComponent

class App : Application() {



    companion object {
        val currentApp = this
        lateinit var applicationComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerAppComponent.builder()
//            .networkModule(NetworkModule)
//            .appModule(AppModule(this))
            .build()
//
        applicationComponent.inject(this)
    }
}