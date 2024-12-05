package com.example.harjoitusprojekti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.harjoitusprojekti.ui.theme.HarjoitusprojektiTheme
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = "user_settings.db"
        ).build()
        val repository = UserSettingsRepository(database.userSettingsDao())
        val mainViewModel = UserSettingsViewModel(repository)

        enableEdgeToEdge()
        setContent {
            HarjoitusprojektiTheme {
                MainApp(mainViewModel)
            }
        }
    }
}

object Routes {
    fun home() = "home"
    fun profile() = "profile"
    fun weather() = "weather"
    fun alarm() = "alarm"
}

@Composable
fun MainApp(mainViewModel: UserSettingsViewModel) {
    val navController = rememberNavController()
    val (title, setTitle) = remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(title) },
        bottomBar = { BottomNavBar(navController) },
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Routes.home()
        ) {
            composable(Routes.home()) {
                HomePage(navController, setTitle, mainViewModel)
            }
            composable(Routes.weather()) {
                WeatherPage(navController, setTitle, mainViewModel)
            }
            composable(Routes.profile()) {
                ProfilePage(navController, setTitle, mainViewModel)
            }
            composable(Routes.alarm()) {
                AlarmPage(navController, setTitle)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(title)
        },
    )
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.home(),
            onClick = { navController.navigate(Routes.home()) },
            icon = { Icon(Icons.Default.Home, contentDescription = Routes.home()) },
            label = { Text(Routes.home()) },
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == Routes.profile(),
            onClick = { navController.navigate(Routes.profile()) },
            icon = { Icon(Icons.Default.Person, contentDescription = Routes.profile()) },
            label = { Text(Routes.profile()) },
        )
    }
}
