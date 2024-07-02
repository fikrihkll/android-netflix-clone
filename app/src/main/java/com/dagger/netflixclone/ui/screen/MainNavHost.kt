package com.dagger.netflixclone.ui.screen

import android.net.Uri
import android.os.Build
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dagger.netflixclone.ui.entity.Profile
import com.dagger.netflixclone.ui.screen.home.HomeScreen
import com.dagger.netflixclone.ui.screen.home.parameter.ProfileParamType
import com.dagger.netflixclone.ui.screen.profileloading.ProfileLoadingScreen
import com.dagger.netflixclone.ui.screen.selectprofile.SelectProfileScreen
import com.google.gson.Gson

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavHost(
    navController: NavHostController
) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = "select-profile") {
            composable(
                route = "home/{profile}",
                arguments = listOf(navArgument("profile") { type = ProfileParamType() })
            ) {
                val profile = if (Build.VERSION.SDK_INT >= 33) it.arguments?.getParcelable("profile", Profile::class.java) else it.arguments?.getParcelable("profile")
                HomeScreen(
                    profile = profile ?: Profile.empty(),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                )
            }
            composable(route = "select-profile") {
                SelectProfileScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    onNavigateToLoadingScreen = {
                        navController.navigate("loading-profile/${Uri.encode(Gson().toJson(it))}")
                    }
                )
            }
            composable(
                route = "loading-profile/{profile}",
                arguments = listOf(navArgument("profile") { type = ProfileParamType() })
            ) {
                val profile = if (Build.VERSION.SDK_INT >= 33) it.arguments?.getParcelable("profile", Profile::class.java) else it.arguments?.getParcelable("profile")
                ProfileLoadingScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    selectedProfile = profile ?: Profile.empty(),
                    onNavigateToHome = {
                        navController.navigate("home/${Uri.encode(Gson().toJson(it))}")
                    }
                )
            }
        }
    }
}