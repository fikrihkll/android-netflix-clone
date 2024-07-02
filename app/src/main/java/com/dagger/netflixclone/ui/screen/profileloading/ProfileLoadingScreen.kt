package com.dagger.netflixclone.ui.screen.profileloading

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.dagger.netflixclone.ui.entity.Profile
import com.dagger.netflixclone.ui.screen.home.components.ProfileCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileLoadingScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    selectedProfile: Profile = Profile.empty(),
    onNavigateToHome: (Profile) -> Unit
) {
    LaunchedEffect(Unit) {
        delay(3000)
        onNavigateToHome.invoke(selectedProfile)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        ProfileCard(
            transitionKey = selectedProfile.id,
            sharedTransitionScope = sharedTransitionScope!!,
            animatedVisibilityScope = animatedVisibilityScope!!,
            showText = false,
            profile = selectedProfile,
            size = (screenWidth/2).dp
        ) {}
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )
        CircularProgressIndicator()
    }
}