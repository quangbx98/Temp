package com.fsoc.template.common.preferences

import android.content.Context
import com.google.gson.Gson

object SharedPrefsHelper {
    private const val PREF_NAME = "Template"

    val gson = Gson()

    fun remove(context: Context, key: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().remove(key).apply()
    }

    fun saveString(context: Context, key: String, value: String?) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(key, value)
            .apply()
    }

    fun getString(context: Context, key: String): String? {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(key, null)
    }


    const val USER = "USER"
    const val SETTING = "SETTING"

}