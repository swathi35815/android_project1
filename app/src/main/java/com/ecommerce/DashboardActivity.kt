package com.ecommerce

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var db: UsersDB = UsersDB.getDatabase(this)
        var h = Handler()

        var textViewUserName = findViewById<TextView>(R.id.textViewDashboardWelcomeUser)
        var intentEmailId = intent.extras!!.getString("keyEmailId")
        GlobalScope.launch {
            var userFullName = intentEmailId?.let { db.usersDao().getFullNameByEmail(it) }
            h.post{
                textViewUserName.setText("Welcome $userFullName")
            }
        }

        var makecall = ApiImageClient.retrofitBuilder.getData()
        makecall.enqueue(object : Callback<List<ImageData>> {
            override fun onResponse(
                call: Call<List<ImageData>>,
                response: Response<List<ImageData>>
            ) {
                var imageResponse : List<ImageData>? = response?.body()
                var recyclerViewDisplayImages = findViewById<RecyclerView>(R.id.recyclerViewDisplayImageData)
                //recyclerViewDisplayImages.layoutManager = LinearLayoutManager(this@DashboardActivity, RecyclerView.VERTICAL, false)
                recyclerViewDisplayImages.layoutManager = GridLayoutManager(this@DashboardActivity, 2, RecyclerView.VERTICAL, false)
                var myAdapter = ImageAdapter(imageResponse)
                recyclerViewDisplayImages.adapter = myAdapter
                myAdapter.notifyDataSetChanged()

                var progressBarImagesLoader = findViewById<ProgressBar>(R.id.progressBarDashboardImageDataLoad)
                if(recyclerViewDisplayImages.isDirty){
                    progressBarImagesLoader.visibility = View.INVISIBLE
                }
                Log.i("mytag", "$imageResponse")
            }

            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                Log.i("mytag", "${t.toString()}")
            }
        })

    }
}
