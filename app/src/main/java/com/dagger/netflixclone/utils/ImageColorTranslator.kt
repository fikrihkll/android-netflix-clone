package com.dagger.netflixclone.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.net.URL

class ImageColorTranslator {

    suspend fun getDominantColor(imageUrl: String): Color? {
        return getBitmapFromUrl(imageUrl)?.let {
            withContext(Dispatchers.Default) {
                val colorInt = Palette
                    .from(it)
                    .generate().darkVibrantSwatch?.rgb
                Log.w("BM", "Bitmap int ${colorInt}")
                colorInt?.let {
                    Color(
                        red = (colorInt shr 16 and 0xFF) / 255.0f,
                        green = (colorInt shr 8 and 0xFF) / 255.0f,
                        blue = (colorInt and 0xFF) / 255.0f,
                        alpha = (colorInt shr 24 and 0xFF) / 255.0f
                    )
                }
            }
        }
    }

    suspend fun getDominantColorAsInt(imageUrl: String): Int? {
        return getBitmapFromUrl(imageUrl)?.let {
            withContext(Dispatchers.Default) {
                val colorInt = Palette
                    .from(it)
                    .generate().vibrantSwatch?.rgb
                Log.w("BM", "Bitmap int ${colorInt}")
                colorInt
            }
        }
    }

    private suspend fun getBitmapFromUrl(
        urlString: String,
    ): Bitmap? {
        val url = URL(urlString)
        return withContext(Dispatchers.IO) {
            try {
                Log.w("BM", "Bitmap loading")
                val connection = url.openConnection()
                connection.connectTimeout = 20000
                val inputStream = connection.getInputStream()
                Log.w("BM", "Bitmap loaded")
                return@withContext BitmapFactory.decodeStream(BufferedInputStream(inputStream))
            } catch (e: Exception) {
                Log.w("BM", "Bitmap err ${e.toString()}")
                return@withContext null
            }
        }
    }

}