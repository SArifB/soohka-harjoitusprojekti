package com.example.harjoitusprojekti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harjoitusprojekti.ui.theme.HarjoitusprojektiTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource


@Composable
fun HomePage(
    navController: NavHostController,
    setTitle: (String) -> Unit = {},
    mainViewModel: UserSettingsViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val user by mainViewModel.userSettings.collectAsState()

    setTitle("Home page")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
    ) {
        Text("Welcome home ${user.name}")
        Button(onClick = { navController.navigate(Routes.weather()) }) {
            Text(stringResource(R.string.home_page_weather_button))
        }
        Button(onClick = { navController.navigate(Routes.profile()) }) {
            Text(stringResource(R.string.home_page_profile_button))
        }
        Button(onClick = { navController.navigate(Routes.alarm()) }) {
            Text(stringResource(R.string.home_page_alarm_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()

    HarjoitusprojektiTheme {
        HomePage(navController)
    }
}
