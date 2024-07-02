package com.dagger.netflixclone.ui.entity

import com.dagger.netflixclone.ui.component.ChipAnimationController

data class TopMenu(
    val id: String,
    val name: String,
    val index: Int,
    val actionable: Boolean = false,
    var animationController: ChipAnimationController? = null,
    val subMenu: List<TopMenu> = listOf()
)
