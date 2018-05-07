package com.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.WorkerThread
import android.support.v4.content.ContextCompat
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by Kevalpatel2106 on 30-Sep-17.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
object ImageUtils {

    /**
     * Save bitmap image to the cached file. You should call this on background thread.
     *
     * @param context Instance of activity
     * @param bitmap  Bitmap to save
     * @return new file or null if error occurs while saving the file.
     */
    @JvmStatic
    @WorkerThread
    fun saveImageFile(context: Context,
                      bitmap: Bitmap): File? {
        var out: FileOutputStream? = null
        try {
            val file = File(FileUtils.getCacheDir(context).absolutePath
                    + "/"
                    + System.currentTimeMillis()
                    + ".png")
            out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            return file
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            try {
                if (out != null) out.close()
            } catch (e: IOException) {
                Timber.e(e)
            }
        }
        return null
    }

    /**
     * Resize the bitmap.
     *
     * @param bitmap Original bitmap.
     * @param maxDimension Max allowed dimensions.
     */
    @JvmStatic
    @WorkerThread
    fun resizeImage(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        //Check if any of the dimensions are larger than max dimensions provided.
        return if (Math.max(width, height) > maxDimension) {
            val aspectRatio: Float = width.toFloat() / height.toFloat()
            return if (width > maxDimension) {  //Width is larger than max allowed dimension
                Bitmap.createScaledBitmap(bitmap, maxDimension, (maxDimension / aspectRatio).toInt(), false)
            } else {   //Height is larger than max allowed dimension
                Bitmap.createScaledBitmap(bitmap, (maxDimension * aspectRatio).toInt(), maxDimension, false)
            }
        } else {
            //Return original bitmap.
            bitmap
        }
    }


    /**
     * Convert Drawable into 50*50 size bitmap
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap? {

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        val bitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888)

        if (bitmap != null) {
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }
        return bitmap
    }

    /**
     * Scale bitmap from resource specifying width height
     */
    @JvmStatic
    fun getScaleBitmapFromResource(context: Context, @DrawableRes resourceID: Int, dimensions: Int): Bitmap {
        // Read your drawable from somewhere
        val dr = ContextCompat.getDrawable(context, resourceID)
        val bitmap = (dr as BitmapDrawable).bitmap
        // Scale it to width x height
        return resizeImage(bitmap, dimensions)
    }

}