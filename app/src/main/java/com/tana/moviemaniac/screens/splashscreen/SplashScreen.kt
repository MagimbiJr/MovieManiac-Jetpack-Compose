package com.tana.moviemaniac.screens.splashscreen

import android.window.SplashScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tana.moviemaniac.components.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    coroutineScope: CoroutineScope,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            delay(3000)
            navHostController.navigate(Screens.HomeScreen.route)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Theaters,
                contentDescription = "",
                modifier = modifier
                    .size(100.dp)
            )
        }
        Text(
            text = "MOVIE MANIAC",
            style = MaterialTheme.typography.h6,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )
    }
}