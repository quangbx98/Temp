package com.fsoc.template.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fsoc.template.common.extension.LOADING
import com.fsoc.template.common.extension.applyIoScheduler
import com.fsoc.template.domain.entity.BaseModel
import com.fsoc.template.domain.usecase.BaseUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var baseUseCase: BaseUseCase

    private val mDisposables = CompositeDisposable()

    val mLoading = MutableLiveData<LOADING>()
    val mError = MutableLiveData<Throwable>()

    val checkAppExpireLiveData = MutableLiveData<BaseModel>()
    val checkMaintenanceModeLiveData = MutableLiveData<BaseModel>()

    protected fun Disposable.track() {
        mDisposables.add(this)
    }

    override fun onCleared() {
        mDisposables.clear()
        super.onCleared()
    }

    fun checkAppExpire() {
        baseUseCase.checkAppExpire()
            .applyIoScheduler(mLoading)
            .subscribe({ checkAppExpire ->
                checkAppExpireLiveData.value = checkAppExpire
            }, { error ->
                mError.value = error
            })
            .track()
    }

    fun checkMaintenanceMode() {
        baseUseCase.checkMaintenanceMode()
            .applyIoScheduler(mLoading)
            .subscribe({ checkStatus ->
                checkMaintenanceModeLiveData.value = checkStatus
            }, { error ->
                mError.value = error
            })
            .track()
    }

    fun unregisterFirebase() {
//        Single.just {
//            FireBaseToken.unregisterToken()
//        }.applyIoScheduler()
//            .subscribe({
//                Logger.d("unregisterFirebase subscribe success")
//            }, { error ->
//                Logger.d("unregisterFirebase subscribe error")
//            })
//            .track()

    }
}