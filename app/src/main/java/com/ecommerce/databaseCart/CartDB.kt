package com.ecommerce.databaseCart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class CartDB : RoomDatabase() {

    abstract fun cartDao(): CartDAOInterface
    companion object {

        @Volatile
        private var INSTANCE: CartDB? = null

        fun getDatabase(context: Context): CartDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDB::class.java,
                    "cartDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}