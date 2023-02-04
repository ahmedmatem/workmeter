package com.ahmedmatem.android.workmeter.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageProxy
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

fun Fragment.setFullScreen() {
    val window = requireActivity().window
    val windowInsetsController =
        WindowCompat.getInsetsController(window, window.decorView)
    // Configure the behavior of the hidden system bars.
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH
    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH

//    windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
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

//    windowInsetsController.show(WindowInsetsCompat.Type.statusBars())
    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
}

/**
 * Params
 * * quality – Hint to the compressor, 0-100. 0 meaning compress for small size,
 * 100 meaning compress for max quality.
 */
@SuppressLint("UnsafeOptInUsageError")
fun ImageProxy.toBitmap(quality: Int = 75) : Bitmap? {
    val yBuffer: ByteBuffer = planes[0].buffer
    val uBuffer: ByteBuffer = planes[1].buffer
    val vBuffer: ByteBuffer = planes[2].buffer

    val ySize: Int = yBuffer.remaining()
    val uSize: Int = uBuffer.remaining()
    val vSize: Int = vBuffer.remaining()

    val n21: ByteArray = ByteArray(ySize + uSize + vSize)
    //U and V are swapped
    yBuffer.get(n21, 0, ySize)
    vBuffer.get(n21, ySize, vSize)
    uBuffer.get(n21, ySize + vSize, uSize)

    val yuvImage: YuvImage = YuvImage(n21, ImageFormat.NV21, image?.width!!, image?.height!!, null)
    val out: ByteArrayOutputStream = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), quality, out)

    return BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size())
}