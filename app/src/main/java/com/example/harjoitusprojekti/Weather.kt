package com.example.harjoitusprojekti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harjoitusprojekti.ui.theme.HarjoitusprojektiTheme


@Composable
fun WeatherPage(
    navController: NavHostController, // unused
    setTitle: (String) -> Unit = {},
    mainViewModel: UserSettingsViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val user by mainViewModel.userSettings.collectAsState()
    val (city, setCity) = remember { mutableStateOf(TextFieldValue(user.city)) }
    val weatherData = fetchWeather(city.text)

    setTitle(stringResource(R.string.weather_page_title))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WeatherCard(
            city = city.text,
            weatherData = weatherData,
        )
        TextInput(
            value = city,
            onValueChange = setCity,
            label = stringResource(R.string.weather_page_input_label),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    val navController = rememberNavController()

    HarjoitusprojektiTheme {
        WeatherPage(navController)
    }
}

@Composable
fun WeatherCard(
    city: String,
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
) {
    @Composable
    fun Field(text: String) = Text(text, style = MaterialTheme.typography.bodyMedium)

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$city ${stringResource(R.string.weather_page_message)}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Field("${stringResource(R.string.weather_page_description)}: ${weatherData.desc}")
            Field("${stringResource(R.string.weather_page_temperature)}: ${weatherData.temp}°C")
            Field("${stringResource(R.string.weather_page_wind_speed)}: ${weatherData.wind} m/s")
        }
    }
}
