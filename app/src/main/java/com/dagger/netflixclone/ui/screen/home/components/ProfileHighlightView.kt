package com.dagger.netflixclone.ui.screen.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.dagger.netflixclone.ui.entity.Profile
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileHighlightView(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    profile: Profile
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val profileOffsetAnimated = remember {
        Animatable(Offset(0f, 0f), Offset.VectorConverter)
    }
    val profileSizeAnimated = remember {
        Animatable((screenWidth/3).toFloat())
    }
    val profileAlphaAnimated = remember {
        Animatable(1f)
    }
    val profileBackgroundColor = remember {
        mutableStateOf(Color.Black)
    }
    val profileBackgroundAnimated by animateColorAsState(
        targetValue = profileBackgroundColor.value,
        label = "profile-background",
        animationSpec = tween(durationMillis = 350)
    )
    var isProfileAnimationShown by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        if (isProfileAnimationShown) {
            return@LaunchedEffect
        }
        delay(1500)
        launch {
            profileBackgroundColor.value = Color.Transparent
        }
        delay(100)
        val job1 = launch {
            profileOffsetAnimated.animateTo(
                Offset(((screenWidth/2) - 40).toFloat(), ((screenHeight/2) - 40).toFloat()),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                )
            )
        }
        val job2 = launch {
            profileSizeAnimated.animateTo(40f)
        }
        val job3 = launch {
            profileAlphaAnimated.animateTo(
                0f,
                animationSpec = tween(delayMillis = 150)
            )
        }
        job1.join()
        job2.join()
        job3.join()
        isProfileAnimationShown = true
    }

    if (!isProfileAnimationShown) {
        ProfileCard(
            modifier = modifier
                .background(profileBackgroundAnimated)
                .alpha(profileAlphaAnimated.value)
                .offset(profileOffsetAnimated.value.x.dp, profileOffsetAnimated.value.y.dp),
            transitionKey = profile.id,
            sharedTransitionScope = sharedTransitionScope!!,
            animatedVisibilityScope = animatedVisibilityScope!!,
            showText = false,
            profile = profile,
            size = profileSizeAnimated.value.dp
        ) {}
    }
}