package com.dagger.netflixclone.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dagger.netflixclone.R
import com.dagger.netflixclone.ui.entity.Poster
import com.dagger.netflixclone.ui.theme.Grey200
import com.dagger.netflixclone.ui.theme.Grey800

@Composable
fun HorizontalPosterList(posters: List<Poster>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posters) { poster ->
            PosterCard(poster)
        }
    }
}

@Composable
fun PosterCard(poster: Poster) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cardWidth = screenWidth / 3.8
    Column(
        modifier = Modifier
            .width(cardWidth.dp)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 6.dp,
                            topEnd = 6.dp
                        )
                    )
                    .aspectRatio(3 / 4f)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(context)
                    .crossfade(true)
                    .data(poster.imageUrl)
                    .build(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "poster_${poster.name}"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    ),
                text = "test",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray),
            progress = { poster.watchedProgress }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 6.dp,
                        bottomEnd = 6.dp
                    )
                )
                .background(color = Grey800),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .size(32.dp),
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "poster info",
                    tint = Grey200
                )
            }
            IconButton(
                modifier = Modifier
                    .size(32.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "poster options",
                    tint = Grey200
                )
            }
        }
    }
}

@Preview
@Composable
fun HorizontalPosterListPreview() {
    HorizontalPosterList(
        posters = listOf(
            Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://cinemags.org/wp-content/uploads/2019/03/game-of-thrones-season-8-targaryen-poster.jpg"),
            Poster(id = "uuid-1234-uuid", name = "name", imageUrl = "https://cinemags.org/wp-content/uploads/2019/03/game-of-thrones-season-8-targaryen-poster.jpg")
        )
    )
}