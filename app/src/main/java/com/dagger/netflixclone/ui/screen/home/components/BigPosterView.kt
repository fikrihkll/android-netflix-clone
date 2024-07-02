package com.dagger.netflixclone.ui.screen.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dagger.netflixclone.R
import com.dagger.netflixclone.ui.theme.Grey200
import com.dagger.netflixclone.ui.theme.Grey800
import com.dagger.netflixclone.utils.outerShadow
import com.dagger.netflixclone.utils.rotationvector.AndroidRotationVector
import kotlinx.coroutines.launch

@Composable
fun BigPoster(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    val gyroscope = remember {
        AndroidRotationVector(context)
    }
    val actualPitch = remember {
        mutableStateOf(0f)
    }
    val actualRoll = remember {
        mutableStateOf(0f)
    }
    val pitchAnimated = remember {
        derivedStateOf {
            if (actualPitch.value > -30) 3f else -3f
        }
    }
    val rollAnimated = remember {
        derivedStateOf {
            if (actualRoll.value > 0) 4f else -4f
        }
    }

    val rotationX by animateFloatAsState(
        targetValue = pitchAnimated.value,
        animationSpec = tween(durationMillis = 650),
        label = "rotation_x"
    )
    val rotationY by animateFloatAsState(
        targetValue = rollAnimated.value,
        animationSpec = tween(durationMillis = 650),
        label = "rotation_y"
    )

    val borderStartOffset by remember {
        derivedStateOf {
            when {
                rotationX > 1f && rotationY > 1f -> Offset(0f, screenWidth.toFloat()) // top right
                rotationX > 1f && rotationY < -1f -> Offset(screenHeight.toFloat(), screenWidth.toFloat()) // top left
                rotationX < -1f && rotationY > 1f -> Offset(0f, 0f) // bottom right
                rotationX < -1f && rotationY < -1f -> Offset(screenHeight.toFloat(), 0f) // bottom left
                else -> Offset(0f, 0f)
            }
        }
    }

    val borderEndOffset by remember {
        derivedStateOf {
            when {
                rotationX > 1f && rotationY > 1f -> Offset(screenHeight.toFloat(), 0f) // top right
                rotationX > 1f && rotationY < -1f -> Offset(0f, 0f) // top left
                rotationX < -1f && rotationY > 1f -> Offset(screenHeight.toFloat(), screenWidth.toFloat()) // bottom right
                rotationX < -1f && rotationY < -1f -> Offset(0f, screenWidth.toFloat()) // bottom left
                else -> Offset(0f, 0f)
            }
        }
    }

    val border1Animated by animateOffsetAsState(
        targetValue = borderStartOffset,
        animationSpec = tween(durationMillis = 550),
        label = "border1"
    )
    val border2Animated by animateOffsetAsState(
        targetValue = borderEndOffset,
        animationSpec = tween(durationMillis = 550),
        label = "border2"
    )

    val dragGestureModifier = Modifier.pointerInput(Unit) {
//        detectDragGestures { _, dragAmount ->
//            rotationXDegrees += dragAmount.y * 0.1f
//            rotationYDegrees -= dragAmount.x * 0.1f
//            Log.w("GYRO", "print gyroscope value X:${rotationXDegrees} : Y${rotationYDegrees}")
//        }
    }

    LaunchedEffect(Unit) {
        gyroscope.streamGyroscopeData().collect {
            launch {
                actualPitch.value = it.pitch
            }
            launch {
                actualRoll.value = it.roll
            }
        }
    }

    Box(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 144.dp, bottom = 24.dp)
            .graphicsLayer {
                this.rotationX = rotationX
                this.rotationY = rotationY
                cameraDistance = 12 * density
            }
            .outerShadow(
                color = Color.White.copy(alpha = 0.1f),
                borderRadius = 16.dp,
                blurRadius = 8.dp,
                offsetX = 1.5.dp,
                offsetY = 1.5.dp,
                spread = 4.dp
            )
            .then(dragGestureModifier),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
                .fillMaxWidth()
                .zIndex(0.1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .weight(0.5f),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Grey200,
                    contentColor = Grey800
                )
            ) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play Icon")
                Text("Play")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                modifier = Modifier
                    .weight(0.5f),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Grey800,
                    contentColor = Grey200
                )
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add To List Icon")
                Text("My List")
            }
        }
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .aspectRatio(3 / 4f)
                .border(
                    width = 3.dp,
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0.0f to Color.White.copy(0.3f),
                            0.1f to Color.Transparent
                        ),
                        start = border1Animated,
                        end = border2Animated
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
                .align(Alignment.Center),
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.thelazyitalian.com/wp-content/uploads/2023/07/iStock-1451071102-1-770x455.jpg")
                .crossfade(true)
                .build(),
            placeholder = painterResource(
                id = R.drawable.ic_launcher_background
            ),
            contentScale = ContentScale.Crop,
            contentDescription = "avatar image",
        )
    }
}