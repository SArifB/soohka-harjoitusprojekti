package com.example.harjoitusprojekti

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
)

data class Weather(
    val description: String,
)

data class Main(
    val temp: Double,
)

data class Wind(
    val speed: Double,
)

data class WeatherData(
    val desc: String,
    val temp: Double,
    val wind: Double,
)

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric",
    ): WeatherResponse

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/"
        private const val API_KEY = "dcf662a3646807175f4839c6ec4a4a1a"

        fun create(): WeatherApi =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
    }
}

@Composable
fun fetchWeather(city: String): WeatherData {
    val service = WeatherApi.create()
    val (weather, setWeather) = remember { mutableStateOf(WeatherData("", 0.0, 0.0)) }

    LaunchedEffect(service) {
        try {
            val response = withContext(Dispatchers.IO) { service.getWeather(city) }
            setWeather(
                WeatherData(
                    desc = response.weather.first().description,
                    temp = response.main.temp,
                    wind = response.wind.speed
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return weather
}
