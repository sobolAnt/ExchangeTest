package com.sobolcorp.exchangetest.views.exchange

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.sobolcorp.exchangetest.R
import com.sobolcorp.exchangetest.customview.CurrencyView
import com.sobolcorp.exchangetest.customview.ExchangeView
import com.sobolcorp.exchangetest.models.Currency
import com.sobolcorp.exchangetest.views.animation.AnimationFragment
import com.sobolcorp.exchangetest.views.main.*
import kotlinx.android.synthetic.main.fragment_exchange.*


class ExchangeFragment : MvpAppCompatFragment(), ExchangeMvpView, MainView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "exchange")
    lateinit var exchangePresenter: ExchangePresenter

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "main")
    lateinit var mainPresenter: MainPresenter

    override fun onResume() {
        super.onResume()
        mainPresenter.currentScreen = Page.EXCHANGE
    }

    override fun onPause() {
        super.onPause()
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(amount.windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animationBtn.setOnClickListener {
            (activity as? AppCompatActivity)?.changeScreen(AnimationFragment())
        }
        amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                try {
                    exchangePresenter.amount = p0.toString().toInt()
                } catch (e: Exception) {
                    exchangePresenter.amount = 0
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        if (exchangePresenter.amount == 0) {
            amount.setText("")
        } else {
            amount.setText("${exchangePresenter.amount}")
        }

        updateCurrency()
        updateSelected()
    }

    override fun updateCurrency() {
        currencyBack.removeAllViews()
        val data = exchangePresenter.currencyList ?: return
        val euro = Currency("EUR", 1f)
        initCurrency(currency = euro, data = data)
        initCurrency("USD", data = data)
        initCurrency("GBP", data = data)
        updateSelected()

        exchangeBack.removeAllViews()
        initExchange(currency = euro, data = data)
        initExchange("USD", data = data)
        initExchange("GBP", data = data)

        euro.also { if(exchangePresenter.selectedCurrency == null) exchangePresenter.selectedCurrency = it }
    }

    override fun amountUpdated() {
        for (i in 0 until currencyBack.childCount) {
            (exchangeBack.getChildAt(i) as? ExchangeView)?.amount = exchangePresenter.amount
        }
    }

    override fun updateSelected() {
        exchangePresenter.selectedCurrency?.also {
            for (i in 0 until currencyBack.childCount) {
                (currencyBack.getChildAt(i) as? CurrencyView)?.selectedCurrencyChanged(it)
            }
            for (i in 0 until currencyBack.childCount) {
                (exchangeBack.getChildAt(i) as? ExchangeView)?.selectedCurrencyChanged(it)
            }
        }
    }

    private fun initCurrency(currencyString: String? = null, currency: Currency? = null, data: List<Currency>) {
        val tempContext = context ?: return
        CurrencyView(tempContext).apply {
            val realCurrency: Currency? = when {
                currency != null -> currency
                else -> data.firstOrNull { it.currency == currencyString }
            }
            realCurrency?.also {
                setCurrency(it)
                selectedCurrencyChanged(it)
                selector = this@ExchangeFragment::currencySelected
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                params.marginStart = 8.dp
                params.marginEnd = 8.dp
                layoutParams = params
                currencyBack.addView(this)
            }
        }
    }

    private fun initExchange(currencyString: String? = null, currency: Currency? = null, data: List<Currency>) {
        val tempContext = context ?: return
        ExchangeView(tempContext).apply {
            val realCurrency: Currency? = when {
                currency != null -> currency
                else -> data.firstOrNull { it.currency == currencyString }
            }
            realCurrency?.also {
                setCurrency(it)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                params.marginStart = 8.dp
                params.marginEnd = 8.dp
                layoutParams = params
                exchangeBack.addView(this)
            }
        }
    }

    private fun currencySelected(currncy: Currency) {
        exchangePresenter.selectedCurrency = currncy
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_exchange, null)


    override fun showUpdate() {
        loadingGroup.visibility = View.VISIBLE
    }

    override fun hideUpdate() {
        Handler().postDelayed({
            if (isResumed)
                loadingGroup.visibility = View.GONE
        }, 500)
    }
}