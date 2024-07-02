package com.dagger.netflixclone.ui.screen.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dagger.netflixclone.ui.component.ChipAnimationController
import com.dagger.netflixclone.ui.entity.TopMenu
import kotlinx.coroutines.delay
import java.util.UUID

class TopMenuManager(
    private val list: SnapshotStateList<TopMenu>,
    private val listState: LazyListState
) {

    val animationControllers: MutableMap<String, ChipAnimationController> = mutableMapOf()

    var currentSelectedMenu: TopMenu? = null
        private set

    suspend fun setSelection(menu: TopMenu) {
        if (currentSelectedMenu != null) {
            return
        }

        currentSelectedMenu = menu
        list.forEach {
            if (it.id != menu.id) {
                animationControllers[it.id]?.animateRemove(
                    onAnimationRemoveFinishListener = {
                        list.removeIf { menu ->
                            currentSelectedMenu!!.id != menu.id
                        }
                    }
                ) ?: run {
                    /**
                     * Not all menu items can be animated since for those
                    which are out of the visible area, aren't rendered and
                    the controllers are not added to the [animationControllers] map

                     * Thus, we have to remove them directly
                     **/
                    list.remove(it)
                }
            }
        }
        delay(350)
        list.addAll(menu.subMenu)
        list.add(index = 0, createCloseButton())
        delay(150)
        listState.animateScrollToItem(0)
    }

    suspend fun removeSelection() {
        if (currentSelectedMenu == null) return

        val currentSelectedId = currentSelectedMenu!!.id
        list.forEach {
            if (it.id != currentSelectedMenu!!.id) {
                animationControllers[it.id]?.animateRemove(
                    onAnimationRemoveFinishListener = {
                        list.removeIf { menu ->
                            currentSelectedId != menu.id
                        }
                    }
                ) ?: run {
                    /**
                     * Not all menu items can be animated since for those
                       which are out of the visible area, aren't rendered and
                       the controllers are not added to the [animationControllers] map

                     * Thus, we have to remove them directly
                     **/
                    list.remove(it)
                }
            }
        }
        delay(350)
        TOP_MENU_LIST.sortedBy {
            it.index
        }.forEach { menu ->
            /**
             * Add the main menu back to the list, except the one previously selected
             * **/
            if (menu.id != currentSelectedMenu!!.id) {
                list.add(menu.index, menu)
            }
        }
        list.removeIf { it.id == CLOSE_BUTTON_ID }
        delay(150)
        listState.animateScrollToItem(0)

        currentSelectedMenu = null
    }

    private fun createCloseButton() = TopMenu(
        id = CLOSE_BUTTON_ID,
        name = "close",
        index = 0,
    )

    companion object {
        const val CLOSE_BUTTON_ID = "close_btn"
        val TOP_MENU_LIST = listOf(
            TopMenu(
                id = UUID.randomUUID().toString(),
                name = "My List",
                index = 0,
            ),
            TopMenu(
                id = UUID.randomUUID().toString(),
                name = "Series",
                index = 1,
                subMenu = listOf(
                    TopMenu(
                        id = UUID.randomUUID().toString(),
                        name = "All Categories",
                        index = -1,
                        actionable = true,
                    ),
                ),
            ),
            TopMenu(
                id = UUID.randomUUID().toString(),
                name = "Movie",
                index = 2,
                subMenu = listOf(
                    TopMenu(
                        id = UUID.randomUUID().toString(),
                        name = "All Categories",
                        index = -1,
                        actionable = true,
                    ),
                ),
            ),
            TopMenu(
                id = UUID.randomUUID().toString(),
                name = "Recently Added",
                index = 3,
            ),
            TopMenu(
                id = UUID.randomUUID().toString(),
                name = "test",
                index = 4,
                actionable = true,
            ),
        )
    }

}