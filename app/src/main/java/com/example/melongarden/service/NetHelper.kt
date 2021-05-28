package com.example.melongarden.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetHelper {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://81.68.104.78:8082/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getRequest(): PostService = retrofit.create(PostService::class.java)

}