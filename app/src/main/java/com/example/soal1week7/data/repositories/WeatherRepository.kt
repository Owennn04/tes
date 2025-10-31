package com.example.soal1week7.data.repositories

import androidx.compose.ui.input.key.Key
import com.example.soal1week7.ui.theme.model.Weather
import com.example.soal1week7.data.services.WeatherService

data class WeatherIcon(val url: String)

class WeatherRepository(
    private val service: WeatherService,
    private val apiKey: String
) {

    suspend fun getCityWeather(cityName: String): Weather {
        val weathers = service.getCityWeather(
            city = cityName,
            units = "metric",
            apiKey = this.apiKey
        )
        return Weather(
            cityName = weathers.name,
            date = weathers.dt,

            weatherIconCode = weathers.weather[0].icon,
            weatherCondition = weathers.weather[0].main,
            temperature = weathers.main.temp,

            humidity = weathers.main.humidity,
            wind = weathers.wind.speed,
            feels = weathers.main.feels_like,
            rainFallLastHour = weathers.rain?.`1h`,
            pressure = weathers.main.pressure,
            cloudsAll = weathers.clouds.all,

            sunrise = weathers.sys.sunrise,
            sunset = weathers.sys.sunset,

            isError = false,
            errorMessage = null
        )
    }

    fun getWeatherIcon(iconId: String): WeatherIcon {
        return WeatherIcon("https://openweathermap.org/img/wn/$iconId@2x.png")
    }
}