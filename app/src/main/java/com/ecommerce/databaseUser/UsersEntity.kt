package com.ecommerce.databaseUser

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["email_id_column"], unique = true)])
class UsersEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_column")
    var userId : Int = 0

    @ColumnInfo(name = "full_name_column")
    var userFullName : String = ""

    @ColumnInfo(name = "email_id_column")
    var userEmailId : String = ""

    @ColumnInfo(name = "phone_number_column")
    var userPhoneNumber : Long = 0

    @ColumnInfo(name = "password_column")
    var userPassword : String = ""
}