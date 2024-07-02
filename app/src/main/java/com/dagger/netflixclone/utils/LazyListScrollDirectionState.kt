package com.dagger.netflixclone.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class LazyListScrollDirectionState(
    private val lazyListState: LazyListState
) {
    private var positionY = lazyListState.firstVisibleItemScrollOffset
    private var visibleItem = lazyListState.firstVisibleItemIndex

    val scrollDirection by derivedStateOf {
        if (lazyListState.isScrollInProgress.not()) {
            ScrollDirection.None
        } else {
            val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset =
                lazyListState.firstVisibleItemScrollOffset

            if (firstVisibleItemIndex == visibleItem) {
                val direction = if (firstVisibleItemScrollOffset > positionY) {
                    ScrollDirection.Down
                } else {
                    ScrollDirection.Up
                }
                positionY = firstVisibleItemScrollOffset

                direction
            } else {

                val direction = if (firstVisibleItemIndex > visibleItem) {
                    ScrollDirection.Down
                } else {
                    ScrollDirection.Up
                }
                positionY = firstVisibleItemScrollOffset
                visibleItem = firstVisibleItemIndex
                direction
            }
        }
    }
}

@Composable
fun rememberCurrentOffset(state: LazyListState): State<Int> {
    val position = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableIntStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.value = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.value += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.value -= (lastItemOffset ?: 0)
        } else {
            currentOffset.value += itemOffset.value
        }
    }

    return currentOffset
}

@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

@Composable
fun <T> rememberRef(): MutableState<T?> {
    return remember {
        object: MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}

enum class ScrollDirection { Up, Down, None }