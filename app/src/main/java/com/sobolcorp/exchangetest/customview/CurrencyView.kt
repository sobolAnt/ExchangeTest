package com.sobolcorp.exchangetest.customview

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import com.sobolcorp.exchangetest.R
import com.sobolcorp.exchangetest.models.Currency
import kotlinx.android.synthetic.main.view_currency.view.*


class CurrencyView : ConstraintLayout, Checkable {

    var localCurrency: Currency? = null
    fun selectedCurrencyChanged(newCurrency: Currency) {
        isChecked = newCurrency == localCurrency
    }
    var selector: ((Currency) -> Unit)? = null

    private val CheckedStateSet = intArrayOf(android.R.attr.state_checked)

    private var state = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setCurrency(currency: Currency) {
        localCurrency = currency
        currencyTitle.text = currency.currency ?: ""
        currencyValue.text = currency.rate?.toString() ?: ""
    }

    init {

        inflate(context, R.layout.view_currency, this)
        background = ResourcesCompat.getDrawable(context.resources, R.drawable.currency_background, null)
        setOnClickListener {
            selector?.invoke(localCurrency?: return@setOnClickListener)
        }
        isChecked = false
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (state) {
            View.mergeDrawableStates(drawableState, CheckedStateSet)
        }
        return drawableState
    }

    override fun isChecked(): Boolean {
        return state
    }

    override fun toggle() {
        state = !state
        refreshDrawableState()
    }

    override fun setChecked(p0: Boolean) {
        if (p0 == state) return
        state = p0
        refreshDrawableState()
    }
}