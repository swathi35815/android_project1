/**
 * Activity class for displaying product details.
 * Displays the ID, title, and URL of a product.
 */
package com.ecommerce

import android.os.Bundle
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_commerce.R

/**
 * AppCompatActivity subclass representing the product details screen.
 */
class DisplayProductDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()

        // Set the layout for the activity
        setContentView(R.layout.activity_display_product_details)

        // Apply window insets listener to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views by their IDs
        val idTextView = findViewById<TextView>(R.id.textViewProductDetailsImageDataID)
        val titleTextView = findViewById<TextView>(R.id.textViewProductDetailsImageDataTitle)
        val urlTextView = findViewById<TextView>(R.id.textViewProductDetailsImageDataURL)

        // Retrieve data from intent extras
        val myBundle = intent.extras
        val idIntent = myBundle?.getString("keyProductId")
        val titleIntent = myBundle?.getString("keyProductTitle")
        val urlIntent = myBundle?.getString("keyProductUrl")

        // Set text to respective TextViews
        idTextView.text = idIntent
        titleTextView.text = titleIntent
        urlTextView.text = urlIntent
    }
}
