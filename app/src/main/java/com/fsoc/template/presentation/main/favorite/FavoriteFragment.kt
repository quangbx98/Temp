package com.fsoc.template.presentation.main.favorite

import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainViewModel

class FavoriteFragment: BaseFragment<MainViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_favorite
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity?:return, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun setUpView() {

    }

    override fun observable() {
    }

    override fun fireData() {
        viewModel.checkAppExpire()
    }
}