package com.tana.moviemaniac.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tana.moviemaniac.presentation.ui.theme.MovieAniTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieAniActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val uiSystemUiController = rememberSystemUiController()
            val coroutineScope = rememberCoroutineScope()

            MovieAniTheme {
                // A surface container using the 'background' color from the theme 
                Surface(color = MaterialTheme.colorScheme.background) {
                    uiSystemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)
                    MovieManiacGraph(
                        navHostController = navController,
                        coroutineScope = coroutineScope
                    )
                }
            }
        }
    }
}
