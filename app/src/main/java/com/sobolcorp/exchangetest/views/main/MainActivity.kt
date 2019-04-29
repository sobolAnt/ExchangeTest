package com.sobolcorp.exchangetest.views.main

import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.sobolcorp.exchangetest.R
import com.sobolcorp.exchangetest.views.animation.AnimationFragment
import com.sobolcorp.exchangetest.views.exchange.ExchangeFragment

fun AppCompatActivity.changeScreen(newFragment: MvpAppCompatFragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, newFragment)
    transaction.commit()
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "main")
    lateinit var mainPresenter: MainPresenter


     fun showExchange() {
        mainPresenter.currentScreen = Page.EXCHANGE
        changeScreen(ExchangeFragment())
    }

     fun showAnimation() {
        mainPresenter.currentScreen = Page.ANIMATION
        changeScreen(AnimationFragment())
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()
        when(mainPresenter.currentScreen) {
            Page.ANIMATION -> showAnimation()
            Page.EXCHANGE -> showExchange()
        }
    }

    override fun onBackPressed() {
        when(mainPresenter.currentScreen) {
            Page.ANIMATION -> showExchange()
            Page.EXCHANGE -> super.onBackPressed()
        }

    }
}
