package com.dagger.netflixclone.ui.screen.home.parameter

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.dagger.netflixclone.ui.entity.Profile
import com.google.gson.Gson

class ProfileParamType : NavType<Profile>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Profile? {
        return if (Build.VERSION.SDK_INT >= 33) bundle.getParcelable(key, Profile::class.java) else bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Profile {
        return Gson().fromJson(value, Profile::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Profile) {
        bundle.putParcelable(key, value)
    }
}