package com.ahmedmatem.android.workmeter.utils

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.setFullScreen() {
    val window = requireActivity().window
    val windowInsetsController =
        WindowCompat.getInsetsController(window, window.decorView)
    // Configure the behavior of the hidden system bars.
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH
    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH

//    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars()) // hide both - navigation and status
    windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
//    windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
}

fun Fragment.clearFullScreen() {
    val window = requireActivity().window
    val windowInsetsController =
        WindowCompat.getInsetsController(window, window.decorView)
    // Configure the behavior of the hidden system bars.
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH
    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH

    windowInsetsController.show(WindowInsetsCompat.Type.navigationBars())
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
}

/**
 * Fragment extension function to save a bitmap in External Storage.
 */
fun Fragment.saveBitmapInGallery(
    bitmap: Bitmap,
    fileNameFormat: String,
    relativePath: String,
    quality: Int = 100
) :Uri? {
    // Create time stamped name and MediaStore entry.
    val name = SimpleDateFormat(fileNameFormat, Locale.UK)
        .format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
        }
    }

    val uri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues,
            null
        )
    } else {
        TODO("VERSION.SDK_INT < R")
    }

    try {
        uri?.let {
            val out: OutputStream? = requireContext().contentResolver.openOutputStream(it)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
        }
    } catch (e: Exception) {
        TODO("Catch bitmap compress exception")
    }

    return uri
}