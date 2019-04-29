package com.sobolcorp.exchangetest.customview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import com.sobolcorp.exchangetest.R
import com.sobolcorp.exchangetest.models.Currency
import kotlinx.android.synthetic.main.view_exchange.view.*

fun calculateAmount(localCurrency: Currency?, selectedCurrency: Currency?, amount: Int): String {
    val local: Float? = localCurrency?.rate
    val selected: Float? = selectedCurrency?.rate
    if (local == null || selected == null) {
       return ""
    }
    return "${(1 + (local - selected)) * amount}"
}

class ExchangeView : ConstraintLayout {

    private var localCurrency: Currency? = null
    private var selectedCurrency: Currency? = null

    var amount = 0
        set(value) {
            field = value
            amountUpdate()
        }

    fun setCurrency(currency: Currency) {
        localCurrency = currency
        currencyTitle.text = currency.currency ?: ""
        amountUpdate()

    }

    fun selectedCurrencyChanged(newSelectedCurrency: Currency) {
        selectedCurrency = newSelectedCurrency
        amountUpdate()
    }

    private fun amountUpdate() {
        currencyExchange.text = calculateAmount(localCurrency, selectedCurrency, amount)
    }

    init {
        inflate(context, R.layout.view_exchange, this)
        background = ResourcesCompat.getDrawable(resources, R.drawable.currency_background_free, null)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}