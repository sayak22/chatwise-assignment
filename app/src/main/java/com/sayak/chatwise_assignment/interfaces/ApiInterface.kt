package com.sayak.chatwise_assignment.interfaces

import com.sayak.chatwise_assignment.models.ApiResponse
import com.sayak.chatwise_assignment.models.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/products")
    fun getProducts(): Call<ApiResponse<List<Product>>>
}