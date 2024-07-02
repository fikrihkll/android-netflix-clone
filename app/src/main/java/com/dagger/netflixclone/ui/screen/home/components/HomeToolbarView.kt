package com.dagger.netflixclone.ui.screen.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.dagger.netflixclone.R
import com.dagger.netflixclone.ui.component.Chip
import com.dagger.netflixclone.ui.component.ChipAnimationController
import com.dagger.netflixclone.ui.screen.home.TopMenuManager
import com.dagger.netflixclone.utils.LazyListScrollDirectionState
import com.dagger.netflixclone.utils.ScrollDirection
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeToolbar(
    modifier: Modifier = Modifier,
    mainListState: LazyListState,
    scrollOffset: State<Int>
) {
    var isTabLayoutVisible by rememberSaveable { mutableStateOf(true) }
    var paddingSize by rememberSaveable { mutableIntStateOf(0) }
    val topMenuList = remember {
        mutableStateListOf(
            *TopMenuManager.TOP_MENU_LIST.toTypedArray()
        )
    }
    val topMenuListState = rememberLazyListState()
    val topMenuManager = remember {
        TopMenuManager(topMenuList, topMenuListState)
    }
    val backgroundAlpha = remember {
        derivedStateOf {
            if (scrollOffset.value > 200) {
                0.3f
            } else {
                0f
            }
        }
    }
    var selectedMenuId by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    val scrollDirection = remember { LazyListScrollDirectionState(mainListState) }
    val paddingSizeAnimated = animateIntAsState(
        targetValue = paddingSize,
        label = "padding_size",
        animationSpec = tween()
    )
    val appBarAlphaAnimated = animateFloatAsState(
        targetValue = backgroundAlpha.value,
        label = "padding_size",
        animationSpec = tween()
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = scrollDirection.scrollDirection) {
        if (scrollDirection.scrollDirection != ScrollDirection.None) {
            if (scrollDirection.scrollDirection == ScrollDirection.Up) {
                isTabLayoutVisible = true
                paddingSize = 0
            } else {
                isTabLayoutVisible = false
                paddingSize = 8
            }
        }
    }

    Column(
        modifier = modifier
            .background(color = Color.Gray.copy(alpha = appBarAlphaAnimated.value))
            .animateContentSize()
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = paddingSizeAnimated.value.dp)
                    .background(color = Color.Transparent),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "For You",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .weight(1f)
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.outline_cast_connected_24),
                        contentDescription = "search icon"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "search icon"
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = isTabLayoutVisible,
            exit = fadeOut() + scaleOut(animationSpec = tween(), targetScale = 0.7f),
            enter = fadeIn() + scaleIn(animationSpec = tween(), initialScale = 0.7f)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                state = topMenuListState
            ) {
                itemsIndexed(
                    items = topMenuList,
                    key = { _, item -> item.id }
                ) {index, menu ->
                    val animationController = remember { ChipAnimationController() }
                    topMenuManager.animationControllers[menu.id] = animationController

                    Chip(
                        modifier = Modifier
                            .animateItemPlacement(
                                animationSpec = tween(durationMillis = 350)
                            ),
                        menu = menu,
                        selectedId = selectedMenuId,
                        onItemClick = {
                            scope.launch {
                                if (it.id == TopMenuManager.CLOSE_BUTTON_ID) {
                                    selectedMenuId = null
                                    topMenuManager.removeSelection()
                                } else {
                                    if (topMenuManager.currentSelectedMenu?.id == it.id) {
                                        selectedMenuId = null
                                        topMenuManager.removeSelection()
                                    } else {
                                        selectedMenuId = it.id
                                        topMenuManager.setSelection(it)
                                    }
                                }
                            }
                        },
                        animationController = animationController
                    )
                }
            }
        }
    }
}