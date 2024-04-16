package com.ecommerce.databaseCart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecommerce.recyclerViewCart.CartItems

@Dao
interface CartDAOInterface {

    // Check if product already exists in cart
    @Query("SELECT * FROM CartEntity WHERE product_id_column = :productId AND user_email_id_column = :userEmailId")
    fun checkIfProductExistsInCart(productId: Int, userEmailId: String) : CartEntity?

    // Add a product only if the product does not exist in cart
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProductToCart(cartEntity: CartEntity)

    // Update product Quantity if the product already exists in cart
    @Query("UPDATE CartEntity SET product_quantity = :productQty WHERE product_id_column = :productId AND user_email_id_column = :userEmailId")
    fun updateProductQty(productQty: Int, productId: Int, userEmailId: String)

    // Remove product from cart if Quantity == 0
    @Query("DELETE FROM CartEntity WHERE product_id_column = :productId AND user_email_id_column = :userEmailId")
    fun removeProductFromCart(productId: Int, userEmailId: String)

    // Read all cart items to display for the current user
    @Query("SELECT product_title_column, product_url_column, product_quantity FROM CartEntity WHERE user_email_id_column = :userEmailId")
    fun readCartItems(userEmailId: String): List<CartItems>?

    // Read product quantity to auto fill when user logins
    @Query("SELECT product_quantity FROM CartEntity WHERE product_id_column = :productId AND user_email_id_column = :userEmailId")
    fun readProductQty(productId: Int, userEmailId: String): Int?

}