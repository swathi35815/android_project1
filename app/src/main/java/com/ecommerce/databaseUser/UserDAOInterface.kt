package com.ecommerce.databaseUser

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAOInterface {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveData(usersEntity: UsersEntity)

    @Query("select * from UsersEntity")
    fun readData() : List<UsersEntity>

    @Query("select * from UsersEntity where email_id_column = :emailId and password_column = :password")
    fun getUserByEmailAndPassword(emailId : String, password : String) : UsersEntity?

    @Query("select * from UsersEntity where email_id_column = :emailId")
    fun getUserByEmail(emailId: String) : UsersEntity?

    @Query("select full_name_column from UsersEntity where email_id_column = :emailId")
    fun getFullNameByEmail(emailId : String) : String?
}