/**
 * Activity class for the dashboard screen.
 * Displays user information and a grid of images fetched from an API.
 */
package com.ecommerce

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.databaseUser.UsersDB
import com.ecommerce.recyclerViewImageData.ApiImageClient
import com.ecommerce.recyclerViewImageData.ImageAdapter
import com.ecommerce.recyclerViewImageData.ImageData
import com.example.e_commerce.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * AppCompatActivity subclass representing the dashboard screen.
 */
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()

        // Set the layout for the activity
        setContentView(R.layout.activity_dashboard)

        // Apply window insets listener to adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtain an instance of the UsersDB
        val db: UsersDB = UsersDB.getDatabase(this)
        val h = Handler()

        // Find views by their IDs
        val textViewUserName = findViewById<TextView>(R.id.textViewDashboardWelcomeUser)
        val intentEmailId = intent.extras!!.getString("keyEmailId")

        // Use coroutine for asynchronous database query
        GlobalScope.launch {
            val userFullName = intentEmailId?.let { db.usersDao().getFullNameByEmail(it) }
            h.post{
                textViewUserName.setText("Welcome $userFullName")
            }
        }

        // Make API call to fetch image data
        val makeCall = ApiImageClient.retrofitBuilder.getData()
        makeCall.enqueue(object : Callback<List<ImageData>> {
            override fun onResponse(call: Call<List<ImageData>>, response: Response<List<ImageData>>) {
                val imageResponse : List<ImageData>? = response.body()
                val recyclerViewDisplayImages = findViewById<RecyclerView>(R.id.recyclerViewDisplayImageData)

                // Set up RecyclerView layout manager and adapter
                recyclerViewDisplayImages.layoutManager = GridLayoutManager(this@DashboardActivity, 1, RecyclerView.VERTICAL, false)
                val myAdapter = ImageAdapter(imageResponse, intentEmailId)
                recyclerViewDisplayImages.adapter = myAdapter
                myAdapter.notifyDataSetChanged()

                // Hide progress bar when data is loaded
                val progressBarImagesLoader = findViewById<ProgressBar>(R.id.progressBarDashboardImageDataLoad)
                if(recyclerViewDisplayImages.isDirty){
                    progressBarImagesLoader.visibility = View.INVISIBLE
                }
                Log.i("mytag", "$imageResponse")
            }

            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                Log.i("mytag", "${t.toString()}")
            }
        })

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        var cartButton = findViewById<Button>(R.id.buttonCartDashboard)
        cartButton.setOnClickListener {
            val cartIntent = Intent(this, CartActivity::class.java)
            cartIntent.putExtra("keyEmailId", intentEmailId)
            startActivity(cartIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionMenuSettings -> {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.actionMenuLogout -> {
                // Handle logout action
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
