package com.fsoc.template.common.extension

import androidx.lifecycle.MutableLiveData
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Disposable.addToCompositedisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> observableTransformer(status: MutableLiveData<LOADING>? = null): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { status?.value = LOADING.START }
            .doFinally { status?.value = LOADING.END }
    }
}


//Observable
fun <T> Observable<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }

fun <T> Observable<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T> Observable<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


//Flowable
fun <T> Flowable<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }

fun <T> Flowable<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T> Flowable<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


//Single
fun <T> Single<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }

fun <T> Single<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T> Single<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


//Maybe
fun <T> Maybe<T>.applyIoScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.io()).doOnSubscribe { status?.value = LOADING.START }
        .doFinally { status?.value = LOADING.END }

fun <T> Maybe<T>.applyComputationScheduler(status: MutableLiveData<LOADING>? = null) =
    applyScheduler(Schedulers.computation()).doOnSubscribe {
        status?.value = LOADING.START
    }
        .doFinally { status?.value = LOADING.END }

private fun <T> Maybe<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())


enum class LOADING {
    START, END
}