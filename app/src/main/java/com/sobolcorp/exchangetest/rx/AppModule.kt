package com.sobolcorp.exchangetest.rx

import android.app.Application
import com.sobolcorp.exchangetest.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val baseApp: App) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return baseApp
    }
}