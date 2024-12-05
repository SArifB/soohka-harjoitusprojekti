package com.example.harjoitusprojekti

import android.content.Intent
import android.provider.AlarmClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harjoitusprojekti.ui.theme.HarjoitusprojektiTheme


@Composable
fun AlarmPage(
    navController: NavHostController, // unused
    setTitle: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    setTitle(stringResource(R.string.alarm_page_title))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
    ) {
        val context = LocalContext.current

        Button(onClick = {
            context.startActivity(Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, context.getString(R.string.alarm_page_message))
                putExtra(AlarmClock.EXTRA_HOUR, 21)
                putExtra(AlarmClock.EXTRA_MINUTES, 32)
            })
        }) {
            Text(stringResource(R.string.alarm_page_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmPreview() {
    val navController = rememberNavController()

    HarjoitusprojektiTheme {
        AlarmPage(navController)
    }
}
