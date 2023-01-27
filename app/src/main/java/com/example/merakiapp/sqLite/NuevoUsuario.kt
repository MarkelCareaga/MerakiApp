package com.example.merakiapp.sqLite

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ActivityNuevoUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.util.*

class NuevoUsuario : AppCompatActivity(){
    private lateinit var binding : ActivityNuevoUsuarioBinding
    private val REQUEST_CODE_TAKE_FOTO = 300
    private val REQUEST_CODE_GALERY = 400

    lateinit var conexion: UsuarioDB
    var foto = false

    lateinit var currentsPhotoPath: String
    lateinit var imageUri: Uri
    lateinit var file: File
    lateinit var imagen:File


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        conexion = UsuarioDB(this)

        binding.imagen.setOnClickListener() {
            checkPermissionStorage()
            // SeleccionarGaleria()
        }

        binding.btnFoto.setOnClickListener(){
            checkPermissionCamera()
        }

        binding.guardarbtn.setOnClickListener {
            if (binding.nombreEt.text.isNotBlank()) {
                val nombre = binding.nombreEt.text.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        val usuarios = conexion.listaTodos()
                        if (usuarios.isNotEmpty()) {
                            if (foto == false) {
                                conexion.insertar_datos(usuarios.last().id + 1, nombre, 0, null)
                            } else {
                                conexion.insertar_datos(usuarios.last().id + 1,
                                    nombre,
                                    0,
                                    currentsPhotoPath)
                                foto = false
                            }
                        } else {
                            if (foto == false) {
                                conexion.insertar_datos(0, nombre, 0, null)
                            } else {

                                conexion.insertar_datos(0, nombre, 0, currentsPhotoPath)
                                foto = false
                            }

                        }
                        startActivity(Intent(this@NuevoUsuario, SeleccionarUsuario::class.java))
                        this@NuevoUsuario.finish()
                    }

            }else{
                Toast.makeText(this,getString(R.string.nombreUsuarioError),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermissionCamera() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                sacaFoto();
                }else{
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CODE_TAKE_FOTO
                    )
                }
            }else{
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    sacaFoto();
                }else{
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CODE_TAKE_FOTO
                    )
                }
            }
        }
    }
    private fun checkPermissionStorage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                galeria()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION),
                    REQUEST_CODE_GALERY

                )
            }
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                galeria();
            }
        }
    }
    //abrir galeria
    private fun galeria() {
        ImageController.selectPhotoFromGallery(this,REQUEST_CODE_GALERY)
        foto = true
    }

    private fun sacaFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager)!= null){
            var photoFile : File? = null

            try {
                photoFile= createFile()
            } catch(e:IOException) {
                e.printStackTrace()
            }

            if (photoFile!= null){
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "com.example.merakiapp",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                foto = true
                startActivityForResult(intent, REQUEST_CODE_TAKE_FOTO)
            }
        }
    }

    private fun createFile(): File  {
        val timestamp = SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(Date())
        val imgFilename = "IMG_" + timestamp

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

         imagen = File.createTempFile(
                imgFilename,
                ".jpg",
                storageDir
            )
        currentsPhotoPath = imagen.absolutePath
        return imagen
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        if (requestCode == REQUEST_CODE_TAKE_FOTO){
            if(permissions.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                sacaFoto()
            }
        }
        else if(requestCode == REQUEST_CODE_GALERY){
            if(permissions.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                galeria()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_TAKE_FOTO -> {
                if (resultCode != Activity.RESULT_OK) {
                    Toast.makeText(this, "No se ha sacado la foto", Toast.LENGTH_SHORT).show()
                } else {
                    binding.imagen.setImageURI(currentsPhotoPath.toUri())
                }
            }
            REQUEST_CODE_GALERY -> {
                if (resultCode != Activity.RESULT_OK) {
                    Toast.makeText(this, "No se ha sacado la foto", Toast.LENGTH_SHORT).show()
                } else {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        imageUri = data.data!!
                        imageUri?.let {
                            val usuarios = conexion.listaTodos()
                            if (usuarios.isNotEmpty()) {
                                ImageController.saveImage(this@NuevoUsuario,usuarios.last().id + 1,it )
                                currentsPhotoPath = ImageController.getImageUri(this@NuevoUsuario, usuarios.last().id + 1)
                                    .toString()


                            } else {
                                ImageController.saveImage(this@NuevoUsuario,0 ,it )
                                currentsPhotoPath = ImageController.getImageUri(this@NuevoUsuario, 0 )
                                    .toString()
                                }
                        }
                        binding.imagen.setImageURI(imageUri)
                    }
                }
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}