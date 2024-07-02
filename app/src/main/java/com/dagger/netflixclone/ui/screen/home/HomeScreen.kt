package com.dagger.netflixclone.ui.screen.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dagger.netflixclone.ui.component.NetflixBottomNavigation
import com.dagger.netflixclone.ui.entity.BottomNavMenu
import com.dagger.netflixclone.ui.entity.Poster
import com.dagger.netflixclone.ui.entity.Profile
import com.dagger.netflixclone.ui.screen.home.components.BigPoster
import com.dagger.netflixclone.ui.screen.home.components.HomeToolbar
import com.dagger.netflixclone.ui.screen.home.components.HorizontalPosterList
import com.dagger.netflixclone.ui.screen.home.components.ProfileHighlightView
import com.dagger.netflixclone.utils.ImageColorTranslator
import com.dagger.netflixclone.utils.rememberCurrentOffset

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    profile: Profile = Profile.empty(),
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val posters = remember {
        mutableStateListOf(
            listOf(
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg", watchedProgress = 0.2f),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg", watchedProgress = 0.7f),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg")
            ),
            listOf(
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg")
            ),
            listOf(
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg")
            ),
            listOf(
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg"),
                Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg")
            ),
        )
    }

    val listState = rememberLazyListState()
    val scrollOffset = rememberCurrentOffset(state = listState)

    var posterColor by rememberSaveable {
        mutableIntStateOf(0)
    }
    val backgroundColor = remember {
        derivedStateOf {
            if (posterColor != 0) {
                if (scrollOffset.value > 400) {
                    Color.Transparent
                } else {
                    Color(
                        red = (posterColor shr 16 and 0xFF) / 255.0f,
                        green = (posterColor shr 8 and 0xFF) / 255.0f,
                        blue = (posterColor and 0xFF) / 255.0f,
                        alpha = (posterColor shr 24 and 0xFF) / 255.0f
                    )
                }
            } else {
                if (scrollOffset.value > 400) {
                    Color.Transparent
                } else {
                    Color.Blue
                }
            }
        }
    }

    val animatedBackgroundColor by animateColorAsState(
        targetValue = backgroundColor.value,
        label = "bg-color",
        animationSpec = tween(1500)
    )

    LaunchedEffect(Unit) {
        val color = ImageColorTranslator().getDominantColorAsInt("https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg")
        color?.let {
            posterColor = color
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to animatedBackgroundColor.copy(0.4f),
                            0.9f to Color.Transparent
                        ),
                        startY = 0f,
                        endY = screenHeight.toFloat()
                    )
                )
                .padding(paddingValues)
        ) {
            ProfileHighlightView(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0.2f)
                    .align(Alignment.Center),
                profile = profile,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )

            HomeToolbar(
                modifier = Modifier
                    .zIndex(0.1f),
                mainListState = listState,
                scrollOffset = scrollOffset
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    BigPoster()
                }
                items(
                    items = posters,
                ) {
                    HorizontalPosterList(
                        posters = it
                    )
                }
            }

            NetflixBottomNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(0.2f),
                menuList = listOf(
                    BottomNavMenu(
                        id = "home",
                        title = "Home",
                        imageVector = Icons.Filled.Home
                    ),
                    BottomNavMenu(
                        id = "new",
                        title = "New & Hot",
                        imageVector = Icons.Filled.PlayArrow
                    ),
                    BottomNavMenu(
                        id = "profile",
                        title = "My Netflix",
                        imageEmoji = "😆"
                    ),
                ),
                onMenuSelected = {}
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}