package com.sobolcorp.exchangetest.views.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.sobolcorp.exchangetest.R
import com.sobolcorp.exchangetest.views.main.MainPresenter
import com.sobolcorp.exchangetest.views.main.MainView
import com.sobolcorp.exchangetest.views.main.Page


class AnimationFragment: MvpAppCompatFragment(), AnimationMvpView, MainView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = "main")
    lateinit var mainPresenter: MainPresenter

    override fun onResume() {
        super.onResume()
        mainPresenter.currentScreen = Page.ANIMATION
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_animation, null)

    @ProvidePresenter
    fun provideAnimationPresenter() = AnimationPresenter()

    override fun startAnimation() {

    }

}