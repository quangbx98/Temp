package com.fsoc.template.domain.entity

data class BaseModel(
    val message: String?,
    val status: Int?,
    val errors: List<String>?
)