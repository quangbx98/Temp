package com.fsoc.template.data.api.entity

data class BaseDto(
    val message: String?,
    val status: Int?,
    val errors: List<String>?
)