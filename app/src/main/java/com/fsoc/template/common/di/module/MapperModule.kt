package com.fsoc.template.common.di.module

import com.fsoc.template.data.api.entity.BaseDto
import com.fsoc.template.data.mapper.BaseDtoMapper
import com.fsoc.template.data.mapper.Mapper
import com.fsoc.template.domain.entity.BaseModel
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    abstract fun provideBaseDto(baseDtoMapper: BaseDtoMapper): Mapper<BaseDto, BaseModel>
}