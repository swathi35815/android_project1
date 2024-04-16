package com.ecommerce.databaseCart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["product_id_column", "user_email_id_column"])
class CartEntity {
    @ColumnInfo(name = "product_id_column")
    var productId: Int = 0

    @ColumnInfo(name = "product_title_column")
    var productTitle: String = ""

    @ColumnInfo(name = "product_url_column")
    var productUrl: String = ""

    @ColumnInfo(name = "product_quantity")
    var productQty: Int = 0

    @ColumnInfo(name = "user_email_id_column")
    var userEmailId: String = ""
}