package com.dagger.netflixclone.ui.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val id: String,
    val name: String,
    val emoji: String
) : Parcelable {
    companion object {
        const val ADD_PROFILE_ID = "add_profile"

        fun addProfile() = Profile(
            id = ADD_PROFILE_ID,
            name = "Add Profile",
            emoji = ""
        )
        fun empty() = Profile("", "", "")
    }
}


