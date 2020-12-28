package com.fsoc.template.common.di.module

import com.fsoc.template.data.repository.BaseRepoImpl
import com.fsoc.template.domain.repository.BaseRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule() {

    @Provides
    @Singleton
    fun provideBaseRepo(repoImpl: BaseRepoImpl): BaseRepo {
        return repoImpl
    }

}
