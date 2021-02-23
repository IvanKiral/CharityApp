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
import androidx.compose.ui.platform.AmbientContext
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.kiral.charityapp.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.Collator
import java.util.*

@Composable
fun loadPicture(
    url: String,
    @DrawableRes defaultImage: Int
): MutableState<Bitmap?> {
    val state: MutableState<Bitmap?> = mutableStateOf(null)

    Glide.with(AmbientContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                state.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    Glide.with(AmbientContext.current)
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


fun sharePhoto(context: Context, url: String) {
    Glide.with(context).asBitmap().load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                Log.i("fds", "fds")
            }

            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {

                val cachePath = File(context.cacheDir, "images")
                CoroutineScope(Dispatchers.IO).launch {
                    cachePath.mkdirs() // don't forget to make the directory
                    try {
                        val stream =
                            FileOutputStream(cachePath.toString() + "/image.png") // overwrites this image every time
                        resource.compress(
                            Bitmap.CompressFormat.PNG,
                            100,
                            stream
                        )
                        val imagePath = File(context.cacheDir, "images")
                        val newFile = File(imagePath, "image.png")

                        val uri: Uri =
                            FileProvider.getUriForFile(
                                context,
                                "${BuildConfig.APPLICATION_ID}.provider",
                                newFile
                            )
                        startSharingIntentWithImage(context, uri)
                    } catch (e: Exception) {

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

fun getCountries(context: Context): Map<String, String> {
    val currentLocale = getCurrentLocale(context)
    return Locale.getISOCountries()
        .map {
            val locale = Locale("", it)
            it.toLowerCase(Locale.ROOT) to locale.getDisplayCountry()
        }.sortedWith { s1, s2 ->
            Collator.getInstance(currentLocale).compare(s1.second, s2.second)
        }
        .toMap()
}


fun getCurrentLocale(context: Context): Locale {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return context.getResources().getConfiguration().getLocales().get(0)
    } else {
        return context.getResources().getConfiguration().locale
    }
}