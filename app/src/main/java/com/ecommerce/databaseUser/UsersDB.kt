package com.ecommerce.databaseUser

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsersEntity::class], version = 1)
abstract class UsersDB : RoomDatabase() {
    abstract fun usersDao() : UserDAOInterface

    companion object {
        @Volatile
        private var INSTANCE: UsersDB? = null

        fun getDatabase(context: Context): UsersDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDB::class.java,
                    "usersDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}