package com.fsoc.template.common.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fsoc.template.common.di.qualifier.ViewModelKey
import com.fsoc.template.presentation.base.ViewModelFactory
import com.fsoc.template.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel


}