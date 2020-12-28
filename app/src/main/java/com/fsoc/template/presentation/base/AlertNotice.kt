package com.fsoc.template.presentation.base

import android.app.AlertDialog
import android.content.Context
import com.fsoc.template.R
import com.orhanobut.logger.Logger

object AlertNotice {

    fun show(
        context: Context?,
        message: String,
        title: String? = null,
        buttonTextId: Int? = null,
        cancelTextId: Int? = null,
        callback: () -> Unit
    ) {
        val alertDialog: AlertDialog? = context?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton(buttonTextId ?: R.string.ok) { dialog, id ->
                    dialog.dismiss()
                    callback.invoke()
                }
                cancelTextId?.let {
                    setNeutralButton(it) { dialog, id ->
                        dialog.dismiss()
                    }
                }
            }

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.apply {
            setCancelable(false)
            Logger.d("show alert")
            show()
        }
    }
}