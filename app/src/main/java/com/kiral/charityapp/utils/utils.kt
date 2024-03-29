package com.kiral.charityapp.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.kiral.charityapp.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.Collator
import java.util.*

object Utils {
    @Composable
    fun loadPicture(
        url: String,
        @DrawableRes defaultImage: Int
    ): MutableState<Bitmap?> {
        val state: MutableState<Bitmap?> = remember { mutableStateOf(null) }
        // show some image when loading is long
        Glide.with(LocalContext.current)
            .asBitmap()
            .load(defaultImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    state.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        // getting actual photo
        Glide.with(LocalContext.current)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    state.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        return state
    }

    @Composable
    fun loadPicture(
        @DrawableRes drawable: Int
    ): MutableState<Bitmap?> {
        val state: MutableState<Bitmap?> = remember { mutableStateOf(null) }
        Glide.with(LocalContext.current)
            .asBitmap()
            .load(drawable)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    state.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        return state
    }

    fun sharePhoto(context: Context, url: String) {
        Glide.with(context).asBitmap().load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val cachePath = File(context.cacheDir, "images")
                    CoroutineScope(Dispatchers.IO).launch {
                        // storing image into cache to be able to share it
                        try {
                            cachePath.mkdirs()
                            val stream = FileOutputStream(cachePath.toString() + "/image.png")
                            resource.compress(
                                Bitmap.CompressFormat.PNG,
                                100,
                                stream
                            )
                            val imagePath = File(context.cacheDir, "images")
                            val newFile = File(imagePath, "image.png")

                            val uri: Uri = FileProvider.getUriForFile(
                                context,
                                "${BuildConfig.APPLICATION_ID}.provider",
                                newFile
                            )
                            startSharingIntentWithImage(context, uri)
                        } catch (e: Exception) {
                            Log.i("AppDebug", "Error: ${e.message}")
                        }
                    }
                }
            }
            )
    }


    fun startSharingIntentWithImage(context: Context, uri: Uri) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }, null)
        share.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(share)
    }

    fun shareLink(context: Context) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "https://cherrities.app")
        }, null)
        share.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(share)
    }

    suspend fun getCountries(context: Context): Map<String, String> {
        return withContext(Dispatchers.IO) {
            val currentLocale = getCurrentLocale(context)
            Locale.getISOCountries()
                .map {
                    val locale = Locale("", it)
                    it.toLowerCase(Locale.ROOT) to locale.getDisplayCountry()
                }.sortedWith { s1, s2 ->
                    // sort also uses special chars in different languages
                    Collator.getInstance(currentLocale).compare(s1.second, s2.second)
                }
                .toMap()
        }
    }

    @Suppress("DEPRECATION")
    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.getResources().getConfiguration().getLocales().get(0)
        } else {
            context.getResources().getConfiguration().locale
        }
    }
}