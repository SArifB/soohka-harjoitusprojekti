package com.example.harjoitusprojekti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harjoitusprojekti.ui.theme.HarjoitusprojektiTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun ProfilePage(
    navController: NavHostController, // unused
    setTitle: (String) -> Unit = {},
    mainViewModel: UserSettingsViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val user by mainViewModel.userSettings.collectAsState()
    val (name, setName) = remember { mutableStateOf(TextFieldValue(user.name)) }
    val (city, setCity) = remember { mutableStateOf(TextFieldValue(user.city)) }

    val saveState = {
        val name = name.text.trim().replaceFirstChar { it.uppercase() }
        val city = city.text.trim().replaceFirstChar { it.uppercase() }
        setName(TextFieldValue(name))
        setCity(TextFieldValue(city))
        mainViewModel.saveUserSettings(name, city)
    }

    setTitle(stringResource(R.string.profile_page_title))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
    ) {
        TextInput(
            value = name,
            onValueChange = setName,
            label = stringResource(R.string.profile_page_name_input_label),
        )
        TextInput(
            value = city,
            onValueChange = setCity,
            label = stringResource(R.string.profile_page_city_input_label),
        )
        Button(
            onClick = saveState,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.profile_page_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()

    HarjoitusprojektiTheme {
        ProfilePage(navController)
    }
}
