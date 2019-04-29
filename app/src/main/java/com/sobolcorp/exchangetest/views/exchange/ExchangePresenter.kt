package com.sobolcorp.exchangetest.views.exchange

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sobolcorp.exchangetest.App
import com.sobolcorp.exchangetest.models.Currency
import com.sobolcorp.exchangetest.network.EcbApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@InjectViewState
class ExchangePresenter : MvpPresenter<ExchangeMvpView>() {
    @Inject
    lateinit var postApi: EcbApi

    var amount: Int = 0
        set(value) {
            if (field != value) {
                field = value
                viewState.amountUpdated()
            }
        }

    var selectedCurrency: Currency? = null
        set(value) {
            if (field != value) {
                field = value
                viewState.updateSelected()
            }
        }

    var currencyList: List<Currency>? = null
        set(value) {
            if (field != value) {
                field = value
                viewState.updateCurrency()
            }
        }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.applicationComponent.inject(this)
        requestCurrency()
    }

    override fun attachView(view: ExchangeMvpView?) {
        super.attachView(view)
        requestCurrency()
    }

    override fun detachView(view: ExchangeMvpView?) {
        super.detachView(view)
        reqiest?.dispose()
    }

    var reqiest: Disposable? = null

    @SuppressLint("CheckResult")
    fun requestCurrency() {
        try {
            reqiest?.dispose()
        } catch (e: Exception) {

        }

        reqiest = postApi.getEuropeanCentralBankExchangeList()
            .observeOn(AndroidSchedulers.mainThread())
            .repeatWhen { observable -> observable.delay(30, TimeUnit.SECONDS) }
            .takeUntil { _ -> false }
            .doOnEach { viewState.showUpdate() }
            .doAfterNext { viewState.hideUpdate() }
            .doFinally { viewState.hideUpdate() }
            .subscribe(
                { result ->
                    currencyList = result.cube?.cube?.cube
                },
                { error ->
                    viewState.showError(error.message ?: "")
                })
    }

}