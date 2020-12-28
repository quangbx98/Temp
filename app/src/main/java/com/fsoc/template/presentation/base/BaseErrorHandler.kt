package com.fsoc.template.presentation.base

import com.orhanobut.logger.Logger
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class BaseErrorHandler @Inject constructor() {

    fun handleMsg(throwable: Throwable): String {
        when (throwable) {
            is HttpException -> {
                Logger.d("HttpException -----------------------")
            }
            is UnknownHostException -> {
                Logger.d("NO INTERNET -----------------------")
                return "NO INTERNET"
            }
            is SocketTimeoutException -> {
                Logger.d("SocketTimeoutException -----------------------")
                return "Socket Timeout"
            }
            else -> throwable.printStackTrace()
        }
        // show only http exception
        return throwable.message ?: "Have error!"
    }
}