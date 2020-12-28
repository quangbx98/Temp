package com.fsoc.template.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fsoc.template.data.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        const val DATABASE_NAME = "mydatabase"
    }
    abstract fun userDao(): UserDao
}