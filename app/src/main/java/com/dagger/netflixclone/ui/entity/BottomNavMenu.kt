package com.dagger.netflixclone.ui.entity

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavMenu(
    val id: String,
    val title: String,
    val imageVector: ImageVector? = null,
    val imageEmoji: String? = null
)