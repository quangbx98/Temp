package com.fsoc.template.presentation

import android.app.Application
import com.fsoc.template.common.di.AppComponent
import com.fsoc.template.common.di.DaggerAppComponent
import com.fsoc.template.common.di.module.ApiModule
import com.fsoc.template.common.di.module.CommonModule
import com.fsoc.template.common.di.module.DaoModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class MyApplication : Application() {

    private lateinit var appComponent: AppComponent

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDI()
        initLogger()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .daoModule(DaoModule(this))
            .apiModule(ApiModule(this))
            .commonModule(CommonModule(this))
            .build()
    }

    private fun initLogger() {
        Logger.addLogAdapter(AndroidLogAdapter())
//        Logger.addLogAdapter(object : AndroidLogAdapter() {
//            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return BuildConfig.DEBUG
//            }
//        })
    }
}