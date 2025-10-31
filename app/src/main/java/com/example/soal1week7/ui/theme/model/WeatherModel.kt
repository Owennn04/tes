package com.example.soal1week7.ui.theme.model

data class Weather(
    val cityName: String = "",
    val date: Int = 0,
    val weatherIconCode: String = "",
    val temperature: Double = 0.0,
    val weatherCondition: String = "",
    val humidity: Int? = null,
    val wind: Double? = null,
    val feels: Double? = null,
    val rainFallLastHour: Double? = null,
    val pressure: Int? = null,
    val cloudsAll: Int? = null,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val isError: Boolean = false,
    val errorMessage: String? = null
)