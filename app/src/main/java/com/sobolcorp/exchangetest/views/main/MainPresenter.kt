package com.sobolcorp.exchangetest.views.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

enum class Page{
    ANIMATION,
    EXCHANGE
}

@InjectViewState
class MainPresenter: MvpPresenter<MainView>() {

   var currentScreen = Page.EXCHANGE

}