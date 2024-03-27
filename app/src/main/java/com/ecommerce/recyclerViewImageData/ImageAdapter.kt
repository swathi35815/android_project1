package com.ecommerce.recyclerViewImageData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.squareup.picasso.Picasso

class ImageAdapter(var imageDataList : List<ImageData>?) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var id = itemView.findViewById<TextView>(R.id.textViewImageDataID)
        var title = itemView.findViewById<TextView>(R.id.textViewImageDataTitle)
        var url = itemView.findViewById<TextView>(R.id.textViewImageDataURL)
        var image = itemView.findViewById<ImageView>(R.id.imageViewImageData)

        fun myBindData(imageData : ImageData){
            id.text = imageData.id.toString()
            title.text = imageData.title
            url.text = imageData.url
            Picasso.with(itemView.context).load(imageData.url).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.MyViewHolder {
        var myView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_images, parent, false)
        return ImageAdapter.MyViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return imageDataList?.size ?: -1
    }

    override fun onBindViewHolder(holder: ImageAdapter.MyViewHolder, position: Int) {
        imageDataList?.get(position)?.let { holder.myBindData(it) }
    }
}