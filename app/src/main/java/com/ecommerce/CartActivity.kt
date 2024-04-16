package com.ecommerce

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.databaseCart.CartDB
import com.ecommerce.databaseUser.UsersDB
import com.ecommerce.recyclerViewCart.CartAdapter
import com.ecommerce.recyclerViewImageData.ImageAdapter
import com.example.e_commerce.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intentEmailId = intent.extras!!.getString("keyEmailId")

        val db: CartDB = CartDB.getDatabase(this)
        val h = Handler()

        val recyclerViewCart = findViewById<RecyclerView>(R.id.recyclerViewDisplayCart)
        recyclerViewCart.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        GlobalScope.launch {
            var cartList = intentEmailId?.let{db.cartDao().readCartItems(it)}
            val myAdapter = CartAdapter(cartList, intentEmailId)
            recyclerViewCart.adapter = myAdapter
            myAdapter.notifyDataSetChanged()
            h.post{
                val progressBarCartListLoader = findViewById<ProgressBar>(R.id.progressBarCartScreen)
                progressBarCartListLoader.visibility = View.INVISIBLE
            }
        }
    }
}