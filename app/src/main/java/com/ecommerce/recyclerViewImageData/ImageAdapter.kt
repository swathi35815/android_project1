/**
 * Adapter class for populating image data in a RecyclerView.
 * Handles the creation of RecyclerView items and binding data to them.
 */
package com.ecommerce.recyclerViewImageData

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.DisplayProductDetails
import com.ecommerce.databaseCart.CartDB
import com.ecommerce.databaseCart.CartEntity
import com.example.e_commerce.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * ViewHolder class representing individual items in the RecyclerView.
 * Responsible for binding image data to the corresponding views.
 */
class ImageAdapter(var imageDataList: List<ImageData>?, var emailIdUser: String?) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    /**
     * ViewHolder class representing individual items in the RecyclerView.
     * @param itemView The view corresponding to the item layout.
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageButton>(R.id.imageButtonImageData)
        var imageTitle = itemView.findViewById<TextView>(R.id.textViewProductTitle)
        var quantity = itemView.findViewById<TextView>(R.id.textViewQty)
        var addQtyButton = itemView.findViewById<ImageButton>(R.id.imageButtonAdd)
        var subtractQtyButton = itemView.findViewById<ImageButton>(R.id.imageButtonSubtract)
        var addToCartButton = itemView.findViewById<Button>(R.id.buttonAddToCart)

        val db: CartDB = CartDB.getDatabase(itemView.context)
        val h = Handler()


        /**
         * Binds image data to the views within the ViewHolder.
         * @param imageData The image data to be displayed.
         */
        fun myBindData(imageData: ImageData) {
            var qty = 0

            Picasso.with(itemView.context).load(imageData.url).into(image)
            imageTitle.setText(imageData.title)

            GlobalScope.launch {
                var qtyFromCart = emailIdUser?.let{db.cartDao().readProductQty(imageData.id, it)}
                h.post{
                    if(qtyFromCart != null) {
                        qty = qtyFromCart
                    }
                    quantity.setText(qty.toString())
                }
            }

            addQtyButton.setOnClickListener {
                if(qty < 20) {
                    qty++
                    quantity.setText(qty.toString())
                }
                else {
                    Toast.makeText(itemView.context, "Maximum quantity reached", Toast.LENGTH_SHORT).show()
                }
            }

            subtractQtyButton.setOnClickListener {
                if(qty > 0) {
                    qty--
                    quantity.setText(qty.toString())
                }
            }

            addToCartButton.setOnClickListener {
                GlobalScope.launch {
                    if(qty != 0){
                        var product = emailIdUser?.let{db.cartDao().checkIfProductExistsInCart(imageData.id, it)}
                        if(product == null) {
                            val cartData = CartEntity().apply{
                                this.productId = imageData.id
                                this.productTitle = imageData.title
                                this.productUrl = imageData.url
                                this.productQty = qty
                                this.userEmailId = emailIdUser.toString()
                            }
                            db.cartDao().addProductToCart(cartData)
                        }
                        else {
                            emailIdUser?.let {db.cartDao().updateProductQty(qty, imageData.id, it)}
                        }
                    }
                    else {
                        emailIdUser?.let{db.cartDao().removeProductFromCart(imageData.id, it)}
                    }
                    emailIdUser?.let{Log.i("myTag", db.cartDao().readCartItems(it).toString())}
                }
            }

            image.setOnClickListener {
                val productDetailsIntent = Intent(it.context, DisplayProductDetails::class.java)
                val myBundle = Bundle().apply {
                    putString("keyProductId", imageData.id.toString())
                    putString("keyProductTitle", imageData.title)
                    putString("keyProductUrl", imageData.url)
                }
                productDetailsIntent.putExtras(myBundle)
                it.context.startActivity(productDetailsIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_images_click, parent, false)
        return MyViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return imageDataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        imageDataList?.get(position)?.let { holder.myBindData(it) }
    }
}
