package com.kiral.charityapp.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import com.kiral.charityapp.utils.Constants.GRAVATAR_LINK
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

val HEX_CHARS = "0123456789ABCDEF".toCharArray()

fun Bitmap.toGrayscale(): Bitmap {
    val width = this.width
    val height = this.height
    val grayPaint = android.graphics.Paint()
    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0f)
    grayPaint.colorFilter = ColorMatrixColorFilter(colorMatrix)
    val bmpGrayscale: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmpGrayscale)
    canvas.drawBitmap(this, 0f, 0f, grayPaint)
    return bmpGrayscale
}

fun Double.convert(): String{
    val format = DecimalFormat("#,###.##")
    val formatSymbols = DecimalFormatSymbols()
    formatSymbols.decimalSeparator = '.'
    formatSymbols.groupingSeparator = ' '
    format.decimalFormatSymbols = formatSymbols
    return format.format(this).toString()
}

fun String.checkCurrencyFormat(): Boolean{
    return this.matches(Regex("|(([1-9]\\d*|0)\\.?(\\d{1,2})?)"))
}

fun String.makeGravatarLink(): String{
    return GRAVATAR_LINK + this.toLowerCase(Locale.ROOT).md5().toLowerCase(Locale.ROOT)
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return hexBinary(md.digest(toByteArray()))
}

fun hexBinary(data: ByteArray): String {
    val r = StringBuilder(data.size * 2)
    data.forEach { b ->
        val i = b.toInt()
        r.append(HEX_CHARS[i shr 4 and 0xF])
        r.append(HEX_CHARS[i and 0xF])
    }
    return r.toString()
}