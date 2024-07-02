package com.dagger.netflixclone.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dagger.netflixclone.ui.entity.TopMenu
import com.dagger.netflixclone.ui.screen.home.TopMenuManager
import kotlinx.coroutines.launch

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    menu: TopMenu,
    animationController: ChipAnimationController?,
    onItemClick: (TopMenu) -> Unit,
    selectedId: String? = null,
    onAddAnimationFinish: ((Int) -> Unit)? = null,
) {

    val translateXAnim = remember {
        Animatable(-50f)
    }
    val alphaAnim = remember {
        Animatable(0f)
    }
    val translateXDefault = 0f
    val alphaDefault = 1f

    var chipWidth by rememberSaveable {
        mutableIntStateOf(0)
    }
    var hasBeenAnimatedInitially by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    animationController?.setListener {
        scope.launch {
            val anim1 = scope.launch {
                translateXAnim.animateTo((chipWidth + (chipWidth/2)).toFloat(), animationSpec = tween())
            }
            val anim2 = scope.launch {
                alphaAnim.animateTo(0f, animationSpec = tween())
            }
            anim1.join()
            anim2.join()
            animationController.onAnimationRemoveFinishListener?.invoke()
        }

    }

    LaunchedEffect(key1 = Unit) {
        if (hasBeenAnimatedInitially) {
            translateXAnim.snapTo(translateXDefault)
            alphaAnim.snapTo(alphaDefault)
        } else {
            hasBeenAnimatedInitially = true
            val anim1 = launch {
                translateXAnim.animateTo(0f, tween(delayMillis = 50))
            }
            val anim2 = launch {
                alphaAnim.animateTo(1f, tween())
            }
            anim1.join()
            anim2.join()
        }
    }

    Box(
        modifier = modifier
            .offset {
                IntOffset(
                    translateXAnim.value.toInt(), 0
                )
            }
            .alpha(alphaAnim.value)
            .border(
                color = Color.Black,
                shape = RoundedCornerShape(32.dp),
                width = 1.dp
            )
            .background(
                color = if (selectedId == menu.id) Color.Gray.copy(alpha = 0.3f) else Color.Transparent,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
            .clickable {
                onItemClick.invoke(menu)
            }
            .onGloballyPositioned {
                chipWidth = it.size.width
            }
    ) {
        if (menu.id == TopMenuManager.CLOSE_BUTTON_ID) {
            Icon(
               imageVector = Icons.Filled.Close,
                contentDescription = "close sub-menu"
            )
        } else {
            Text(text = menu.name)
        }
    }
}

class ChipAnimationController {

    private var onItemRemoveListener: (() -> Unit)? = null
    var onAnimationRemoveFinishListener: (() -> Unit)? = null

    fun setListener(
        onItemRemoveListener: () -> Unit
    ) {
        this.onItemRemoveListener = onItemRemoveListener
    }

    fun animateRemove(onAnimationRemoveFinishListener: () -> Unit) {
        this.onAnimationRemoveFinishListener = onAnimationRemoveFinishListener
        onItemRemoveListener?.invoke()
    }

}