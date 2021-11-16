package com.tana.moviemaniac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tana.moviemaniac.ui.theme.MovieAniTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieAniActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val uiSystemUiController = rememberSystemUiController()
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()

            MovieAniTheme {
                // A surface container using the 'background' color from the theme 
                Surface(color = MaterialTheme.colors.background) {
                    uiSystemUiController.setSystemBarsColor(MaterialTheme.colors.background)
                    MovieAniGraph(
                        navHostController = navController,
                        scaffoldState = scaffoldState,
                        coroutineScope = coroutineScope
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieAniTheme {
        Greeting("Android")
    }
}