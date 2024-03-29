/**
 * Database class for managing users' data using Room Database.
 */
package com.ecommerce.databaseUser

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Annotates class to be a Room Database with a table (entity) of UsersEntity.
 */
@Database(entities = [UsersEntity::class], version = 1)
abstract class UsersDB : RoomDatabase() {

    /**
     * Abstract method that returns UserDAOInterface.
     * This method is used to access UserDAOInterface methods for interacting with the database.
     */
    abstract fun usersDao(): UserDAOInterface

    /**
     * Companion object to provide a singleton instance of UsersDB.
     */
    companion object {
        /**
         * Volatile keyword ensures that the value of INSTANCE is always up-to-date and visible to all threads.
         */
        @Volatile
        private var INSTANCE: UsersDB? = null

        /**
         * Returns an instance of UsersDB. If an instance already exists, it returns the existing instance.
         * If no instance exists, it creates a new instance using Room's databaseBuilder.
         * This method is thread-safe, ensuring that only one instance of the database is created.
         *
         * @param context Application context used for creating the database instance.
         * @return Instance of UsersDB.
         */
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
