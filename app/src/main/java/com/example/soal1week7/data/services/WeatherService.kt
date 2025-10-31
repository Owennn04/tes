package com.example.soal1week7.data.services

import com.example.soal1week7.data.dto.WeatherModelNew
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getCityWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherModelNew
}