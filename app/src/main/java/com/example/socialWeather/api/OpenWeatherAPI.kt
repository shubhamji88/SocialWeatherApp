package com.example.socialWeather.api

import com.example.socialWeather.api.response.WeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val BASE_URL="https://api.openweathermap.org/"
private val okHttpLogger= HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttp= OkHttpClient.Builder().addInterceptor(okHttpLogger).build()


private val retrofit= Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttp)
    .build()
interface ApiService{
    @GET("data/2.5/weather?q=delhi,in&units=metric&appid=")
    fun getWeather(): Deferred<WeatherResponse>
}
object WeatherApi{
    val retrifitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
