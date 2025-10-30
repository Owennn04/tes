package com.example.soal1week7.data.container

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherServerContainer {
    val BASE_URL = "https://api.openweathermap.org/data/2.5"
    val API_KEY = "044fd15643fffee8b05e61033044492a"


    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(API_KEY))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

}
class AuthInterceptor(private val bearerToken: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(request)
    }
}