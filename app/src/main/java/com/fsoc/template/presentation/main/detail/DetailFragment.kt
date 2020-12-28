package com.fsoc.template.presentation.main.detail

import androidx.lifecycle.ViewModelProviders
import com.fsoc.template.R
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.presentation.base.BaseFragment
import com.fsoc.template.presentation.main.MainViewModel

class DetailFragment: BaseFragment<MainViewModel>() {
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_detail
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