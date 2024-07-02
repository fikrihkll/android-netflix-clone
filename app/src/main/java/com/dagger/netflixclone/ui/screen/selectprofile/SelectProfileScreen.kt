package com.dagger.netflixclone.ui.screen.selectprofile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dagger.netflixclone.ui.entity.Profile
import com.dagger.netflixclone.ui.screen.home.components.ProfileCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SelectProfileScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onNavigateToLoadingScreen: (Profile) -> Unit
) {
    var isSelectionMode by rememberSaveable {
        mutableStateOf(true)
    }
    var selectedProfile by remember {
        mutableStateOf<Profile?>(null)
    }
    val profileList = listOf(Profile(id = "zach", name = "Zach", emoji = "😆"), Profile(id = "stephanie", name = "Stephanie", emoji = "☺️"))

    Scaffold { paddingValues ->
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            FlowRow(
                modifier = Modifier.align(Alignment.Center),
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                for (item in profileList) {
                    ProfileCard(
                        onClick = { onNavigateToLoadingScreen.invoke(item) },
                        profile = item,
                        size = (screenWidth/3.5).dp,
                        animatedVisibilityScope = animatedVisibilityScope!!,
                        sharedTransitionScope = sharedTransitionScope!!,
                        transitionKey = item.id
                    )
                }
                ProfileCard(
                    onClick = { /*onProfileClick.invoke(Profile.empty())*/ },
                    profile = Profile.addProfile(),
                    size = (screenWidth/3.5).dp,
                    animatedVisibilityScope = animatedVisibilityScope!!,
                    sharedTransitionScope = sharedTransitionScope!!,
                    transitionKey = "add"
                )
            }
        }
    }
}