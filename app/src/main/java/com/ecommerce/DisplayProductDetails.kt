package com.ecommerce

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_commerce.R

class DisplayProductDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display_product_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var idTextView = findViewById<TextView>(R.id.textViewProductDetailsImageDataID)
        var titleTextView = findViewById<TextView>(R.id.textViewProductDetailsImageDataTitle)
        var urlTextView = findViewById<TextView>(R.id.textViewProductDetailsImageDataURL)

        var mybundle = intent.extras
        var idIntent = mybundle?.getString("keyProductId")
        var titleIntent = mybundle?.getString("keyProductTitle")
        var urlIntent = mybundle?.getString("keyProductUrl")

        idTextView.setText(idIntent)
        titleTextView.setText(titleIntent)
        urlTextView.setText(urlIntent)
    }
}