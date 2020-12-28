package com.fsoc.template.presentation.base

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(var creators: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    @NonNull
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]

        if (creator == null) {
            creator = creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
        }

        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}