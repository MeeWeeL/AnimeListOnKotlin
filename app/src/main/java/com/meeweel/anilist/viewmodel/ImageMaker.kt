package com.meeweel.anilist.viewmodel

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.meeweel.anilist.viewmodel.Changing.getContext
import java.io.*
import java.net.URL


class ImageMaker() {

    var cw: ContextWrapper = ContextWrapper(getContext())
    var directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)

    fun savePictureToDirectory(pictureLink: String, pictureName: String) {
        lateinit var fos: FileOutputStream
        lateinit var mypath: File
        val jpg = "$pictureName.jpeg"
        mypath = File(directory, jpg)
        Thread {
            try {
                val url = URL(pictureLink)
                val bitMapPic = BitmapFactory.decodeStream(url.openStream())
                val sizer = bitMapPic.width/400
                val w = bitMapPic.width/sizer
                val h = bitMapPic.height/sizer
                val bitmap = Bitmap.createScaledBitmap(bitMapPic, w, h, false)
                try {
                    fos = FileOutputStream(mypath)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, fos)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: IOException) {

            }
        }.start()
    }

    fun getPictureFromDirectory(pictureName: String) : Bitmap {
        lateinit var bitMapPic: Bitmap
        try {
            val f: File = File(directory.absolutePath, "$pictureName.jpeg")
            bitMapPic = BitmapFactory.decodeStream(FileInputStream(f))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bitMapPic
    }
}