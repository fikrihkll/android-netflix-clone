package com.dagger.netflixclone.ui.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun RectangleShimmer(
    modifier: Modifier = Modifier,
    index: Int = 0
) {
    val infiniteAnimation = rememberInfiniteTransition(label = "rectangle-shimmer")

    val alpha by infiniteAnimation.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                delayMillis = index * 100
                durationMillis = 750
                0.8f at 500
                0.9f at 650
            },
            repeatMode = RepeatMode.Reverse,
        ), label = "alpha-infinite"
    )

    Box(
        modifier = modifier
            .alpha(alpha = alpha)
            .background(Color.Gray)
    )
}

@Composable
fun CircleShimmer(
    modifier: Modifier = Modifier,
    size: Dp,
    index: Int = 0
) {
    val infiniteAnimation = rememberInfiniteTransition(label = "circle-shimmer")

    val alpha by infiniteAnimation.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                delayMillis = index * 100
                durationMillis = 750
                0.8f at 500
                0.9f at 650
            },
            repeatMode = RepeatMode.Reverse,
        ), label = "alpha-infinite"
    )

    Box(
        modifier = modifier
            .height(size)
            .width(size)
            .alpha(alpha = alpha)
            .clip(CircleShape)
            .background(Color.Gray)
    )
}