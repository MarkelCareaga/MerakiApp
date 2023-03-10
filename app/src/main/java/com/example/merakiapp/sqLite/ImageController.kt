package com.example.merakiapp.sqLite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import java.io.File

object ImageController {

    // Abrir la galeria del movil
    fun selectPhotoFromGallery(activity: Activity, code: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, code)

    }

    // guardar la imagen seleccionada de la galeria
    @SuppressLint("Recycle")
    fun saveImage(context: Context, id: Int, uri: Uri) {
        val imgFilename = "IMG_$id"
        val file = File(context.filesDir, imgFilename)
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()!!

        file.writeBytes(bytes)
    }

    //obtener la imagen seleccionada de la galeria
    fun getImageUri(context: Context, id: Int): Uri {
        val imgFilename = "IMG_$id"
        val file = File(context.filesDir, imgFilename)
        return if (file.exists()) {
            Uri.fromFile(file)
        } else {
            Uri.parse(Environment.DIRECTORY_PICTURES)
        }
    }
}