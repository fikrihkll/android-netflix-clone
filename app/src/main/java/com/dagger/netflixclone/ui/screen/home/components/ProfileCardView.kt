package com.dagger.netflixclone.ui.screen.home.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dagger.netflixclone.ui.entity.Profile


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    transitionKey: String,
    size: Dp,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    showText: Boolean = true,
    profile: Profile,
    onClick: (Profile) -> Unit
) {
    val boundsTransform = { _: Rect, _: Rect -> tween<Rect>(550) }

    sharedTransitionScope.run {
        Column(
            modifier = modifier
                .clickable {
                    onClick.invoke(profile)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .size(size)
                    .sharedElement(
                        rememberSharedContentState(key = transitionKey),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = boundsTransform
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, color = Color.DarkGray),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (profile.id == Profile.ADD_PROFILE_ID) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add profile",
                            modifier = Modifier.size(60.dp),
                        )
                    } else {
                        Text(
                            text = profile.emoji,
                            fontSize = (size.value/3).sp,
                        )

                    }
                }

            }
            if (showText) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = profile.name, textAlign = TextAlign.Center)
            }
        }
    }

}