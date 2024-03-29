/**
 * Retrofit interface for defining API endpoints related to image data.
 */
package com.ecommerce.recyclerViewImageData

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Annotates interface as a Retrofit service interface.
 * Defines a method for retrieving image data from the specified endpoint.
 */
interface ApiImageInterface {
    /**
     * Makes a GET request to the "photos" endpoint to fetch image data.
     *
     * @return A Retrofit Call object representing the asynchronous request for image data.
     */
    @GET("photos")
    fun getData(): Call<List<ImageData>>
}

/**
 * Client class for creating Retrofit instances to access the API endpoints.
 */
class ApiImageClient {
    companion object {
        /**
         * Builds and provides a Retrofit instance for accessing the API endpoints.
         */
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // Base URL for the API
            .addConverterFactory(GsonConverterFactory.create()) // Converter factory for Gson
            .build()
            .create(ApiImageInterface::class.java) // Create an implementation of the API endpoints
    }
}
