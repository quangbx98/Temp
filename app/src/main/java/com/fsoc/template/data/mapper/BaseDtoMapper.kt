package com.fsoc.template.data.mapper

import com.fsoc.template.data.api.entity.BaseDto
import com.fsoc.template.domain.entity.BaseModel
import javax.inject.Inject

class BaseDtoMapper @Inject constructor() : Mapper<BaseDto, BaseModel>() {
    override fun map(from: BaseDto): BaseModel {
        return from.let {
            BaseModel(message = it.message, status = it.status, errors = it.errors)
        }
    }

    override fun reverse(to: BaseModel): BaseDto {
        return to.let {
            BaseDto(
                message = it.message,
                status = it.status,
                errors = it.errors
            )
        }
    }
}