package com.sobolcorp.exchangetest.views.exchange

import com.arellomobile.mvp.MvpView

interface ExchangeMvpView: MvpView {

    fun showUpdate()
    fun hideUpdate()

    fun updateSelected()
    fun amountUpdated()

    fun updateCurrency()
    fun showError(error: String)

}