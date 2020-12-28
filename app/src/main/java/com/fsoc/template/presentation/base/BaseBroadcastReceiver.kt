package com.fsoc.template.presentation.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.orhanobut.logger.Logger

class BaseBroadcastReceiver(private val broadCastCallback: (data: String, body: String?) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Logger.d(intent)

        val data = intent.getStringExtra("data")
        val body = intent.getStringExtra("body")
        Logger.d("data: $data")
        Logger.d("body: $body")
        data?.let {
            broadCastCallback.invoke(data, body)
        }
    }

}