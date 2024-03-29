/**
 * Data Access Object (DAO) interface for managing user data in the database.
 */
package com.ecommerce.databaseUser

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Annotates interface as a Room DAO (Data Access Object).
 * This interface defines methods for interacting with the UsersEntity table in the database.
 */
@Dao
interface UserDAOInterface {
    /**
     * Inserts a new user entity into the database.
     * If a user with the same primary key already exists, it ignores the insertion.
     *
     * @param usersEntity The UsersEntity object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveData(usersEntity: UsersEntity)

    /**
     * Retrieves all user entities from the database.
     *
     * @return A list of UsersEntity objects representing all users stored in the database.
     */
    @Query("SELECT * FROM UsersEntity")
    fun readData(): List<UsersEntity>

    /**
     * Retrieves a user entity from the database based on the provided email and password.
     *
     * @param emailId The email ID of the user.
     * @param password The password of the user.
     * @return The UsersEntity object representing the user with the specified email and password,
     *         or null if no such user exists.
     */
    @Query("SELECT * FROM UsersEntity WHERE email_id_column = :emailId AND password_column = :password")
    fun getUserByEmailAndPassword(emailId: String, password: String): UsersEntity?

    /**
     * Retrieves a user entity from the database based on the provided email.
     *
     * @param emailId The email ID of the user.
     * @return The UsersEntity object representing the user with the specified email,
     *         or null if no such user exists.
     */
    @Query("SELECT * FROM UsersEntity WHERE email_id_column = :emailId")
    fun getUserByEmail(emailId: String): UsersEntity?

    /**
     * Retrieves the full name of a user from the database based on the provided email.
     *
     * @param emailId The email ID of the user.
     * @return The full name of the user with the specified email, or null if no such user exists.
     */
    @Query("SELECT full_name_column FROM UsersEntity WHERE email_id_column = :emailId")
    fun getFullNameByEmail(emailId: String): String?
}
