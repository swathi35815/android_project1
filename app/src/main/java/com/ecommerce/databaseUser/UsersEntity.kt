/**
 * Entity class representing the UsersEntity table in the database.
 */
package com.ecommerce.databaseUser

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Annotates class as a Room Entity representing the UsersEntity table.
 * The indices annotation ensures uniqueness of email_id_column values.
 */
@Entity(indices = [Index(value = ["email_id_column"], unique = true)])
class UsersEntity {

    /**
     * Primary key column representing the unique identifier of each user.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_column")
    var userId: Int = 0

    /**
     * Column representing the full name of the user.
     */
    @ColumnInfo(name = "full_name_column")
    var userFullName: String = ""

    /**
     * Column representing the email ID of the user.
     * It is indexed for efficient querying and ensures uniqueness.
     */
    @ColumnInfo(name = "email_id_column")
    var userEmailId: String = ""

    /**
     * Column representing the phone number of the user.
     */
    @ColumnInfo(name = "phone_number_column")
    var userPhoneNumber: Long = 0

    /**
     * Column representing the password of the user.
     */
    @ColumnInfo(name = "password_column")
    var userPassword: String = ""
}
