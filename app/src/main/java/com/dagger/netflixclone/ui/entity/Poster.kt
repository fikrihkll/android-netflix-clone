package com.dagger.netflixclone.ui.entity

data class Poster(
    val id: String,
    val name: String,
    val imageUrl: String,
    val watchedProgress: Float = 0f
)