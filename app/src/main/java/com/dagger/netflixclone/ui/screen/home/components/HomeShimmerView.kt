package com.dagger.netflixclone.ui.screen.home.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dagger.netflixclone.ui.component.RectangleShimmer

@Composable
fun HomeShimmer() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RectangleShimmer(
            modifier = Modifier
                .width((screenWidth - (80.dp).value).dp)
                .aspectRatio(3 / 4f)
                .clip(RoundedCornerShape(12.dp)),
        )
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            for (i in 0..8) {
                RectangleShimmer(
                    modifier = Modifier
                        .width((screenWidth / 3.8).dp)
                        .aspectRatio(3 / 4f)
                        .clip(RoundedCornerShape(12.dp)),
                    index = 1
                )
                if (i < 8)
                    Spacer(modifier = Modifier.width(12.dp))
            }
        }
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            for (i in 0..8) {
                RectangleShimmer(
                    modifier = Modifier
                        .width((screenWidth / 3.8).dp)
                        .aspectRatio(3 / 4f)
                        .clip(RoundedCornerShape(12.dp)),
                    index = 2
                )
                if (i < 8)
                    Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

@Preview
@Composable
private fun HomeShimmerPreview() {
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        HomeShimmer()
    }
}