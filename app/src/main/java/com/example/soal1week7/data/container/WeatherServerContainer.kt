package com.example.soal1week7.data.container

import com.example.soal1week7.data.repositories.WeatherRepository
import com.example.soal1week7.data.services.WeatherService
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherServerContainer {
    val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val API_KEY = "044fd15643fffee8b05e61033044492a"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val weatherService: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherService)
    }
}