package com.ecommerce.recyclerViewImageData

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.DisplayProductDetails
import com.example.e_commerce.R
import com.squareup.picasso.Picasso
import android.app.Activity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity

class ImageAdapter(var imageDataList : List<ImageData>?) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var id = itemView.findViewById<TextView>(R.id.textViewImageDataID)
        var title = itemView.findViewById<TextView>(R.id.textViewImageDataTitle)
        var url = itemView.findViewById<TextView>(R.id.textViewImageDataURL)
        var image = itemView.findViewById<ImageView>(R.id.imageButtonImageData)

        fun myBindData(imageData : ImageData){
            /*id.text = imageData.id.toString()
            title.text = imageData.title
            url.text = imageData.url*/
            Picasso.with(itemView.context).load(imageData.url).into(image)
            var imageButton = itemView.findViewById<ImageButton>(R.id.imageButtonImageData)
            imageButton.setOnClickListener {
                var productDetailsIntent = Intent(it.context, DisplayProductDetails::class.java)
                var myBundle = Bundle()
                myBundle.putString("keyProductId", imageData.id.toString())
                myBundle.putString("keyProductTitle", imageData.title)
                myBundle.putString("keyProductUrl", imageData.url)
                productDetailsIntent.putExtras(myBundle)
                it.context.startActivity(productDetailsIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.MyViewHolder {
        var myView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_images_click, parent, false)
        return ImageAdapter.MyViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return imageDataList?.size ?: -1
    }

    override fun onBindViewHolder(holder: ImageAdapter.MyViewHolder, position: Int) {
        imageDataList?.get(position)?.let { holder.myBindData(it) }
    }
}