/**
 * Adapter class for populating image data in a RecyclerView.
 * Handles the creation of RecyclerView items and binding data to them.
 */
package com.ecommerce.recyclerViewImageData

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.DisplayProductDetails
import com.example.e_commerce.R
import com.squareup.picasso.Picasso

/**
 * ViewHolder class representing individual items in the RecyclerView.
 * Responsible for binding image data to the corresponding views.
 */
class ImageAdapter(var imageDataList: List<ImageData>?) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    /**
     * ViewHolder class representing individual items in the RecyclerView.
     * @param itemView The view corresponding to the item layout.
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageButton>(R.id.imageButtonImageData)

        /**
         * Binds image data to the views within the ViewHolder.
         * @param imageData The image data to be displayed.
         */
        fun myBindData(imageData: ImageData) {
            Picasso.with(itemView.context).load(imageData.url).into(image)
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
