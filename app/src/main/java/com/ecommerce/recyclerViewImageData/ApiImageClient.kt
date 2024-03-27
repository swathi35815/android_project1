package com.ecommerce.recyclerViewImageData

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiImageInterface{
    @GET("photos")
    fun getData() : Call<List<ImageData>>
}
class ApiImageClient {
    companion object{
        var retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiImageInterface::class.java)
    }
}