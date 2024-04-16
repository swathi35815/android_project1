package com.ecommerce.recyclerViewCart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.squareup.picasso.Picasso

class CartAdapter (var cartList: List<CartItems>?, var emailIdUser: String?) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImage = itemView.findViewById<ImageButton>(R.id.imageButtonImageDataCart)
        var productTitle = itemView.findViewById<TextView>(R.id.textViewProductTitleCart)
        var productQty = itemView.findViewById<TextView>(R.id.textViewProductQuantityCart)
        fun myBindData(cartItem : CartItems) {
            Picasso.with(itemView.context).load(cartItem.product_url_column).into(productImage)
            productTitle.setText(cartItem.product_title_column)
            productQty.setText(cartItem.product_quantity.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return cartList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        cartList?.get(position)?.let { holder.myBindData(it) }
    }
}