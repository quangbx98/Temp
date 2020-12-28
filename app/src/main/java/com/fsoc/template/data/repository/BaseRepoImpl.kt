package com.fsoc.template.data.repository

import com.fsoc.template.data.api.BaseApi
import com.fsoc.template.data.api.entity.BaseDto
import com.fsoc.template.data.mapper.Mapper
import com.fsoc.template.domain.entity.BaseModel
import com.fsoc.template.domain.repository.BaseRepo
import io.reactivex.Single
import javax.inject.Inject

class BaseRepoImpl @Inject constructor() : BaseRepo {
    @Inject
    lateinit var baseApi: BaseApi
    @Inject
    lateinit var mapperBaseDto: Mapper<BaseDto, BaseModel>

    override fun checkAppExpire(): Single<BaseModel> {
        return baseApi.getCheckAppExpire().map {
            return@map mapperBaseDto.map(it)
        }
    }

    override fun checkMaintenanceMode(): Single<BaseModel> {
        return baseApi.checkMaintenanceMode().map {
            return@map mapperBaseDto.map(it)
        }
    }
}