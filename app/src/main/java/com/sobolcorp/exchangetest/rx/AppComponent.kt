package com.sobolcorp.exchangetest.rx

import com.sobolcorp.exchangetest.App
import com.sobolcorp.exchangetest.network.NetworkModule
import com.sobolcorp.exchangetest.views.exchange.ExchangePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {


    fun inject(app: App)
    fun inject(presenter: ExchangePresenter)
}