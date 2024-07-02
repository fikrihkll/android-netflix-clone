package com.dagger.netflixclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dagger.netflixclone.ui.screen.MainNavHost
import com.dagger.netflixclone.ui.screen.home.HomeScreen
import com.dagger.netflixclone.ui.theme.NetflixCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetflixCloneApp()
        }
    }
}

@Composable
fun NetflixCloneApp() {
    NetflixCloneTheme {
        val navController = rememberNavController()
        MainNavHost(navController = navController)
    }
}