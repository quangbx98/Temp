package com.fsoc.template.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey var uid: String = "",
    @ColumnInfo(name = "email") var email: String = "",
    @ColumnInfo(name = "username") var username: String = "",
    @ColumnInfo(name = "password") var password: String = "",
    @ColumnInfo(name = "fullName") var fullName: String = "",
    @ColumnInfo(name = "avatar") var avatar: String = ""
)