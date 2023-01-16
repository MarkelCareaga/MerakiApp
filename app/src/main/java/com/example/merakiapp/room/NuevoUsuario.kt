package com.example.merakiapp.room

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.example.merakiapp.databinding.ActivityNuevoUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.sql.Blob
import java.util.*

class NuevoUsuario : AppCompatActivity(){
    private lateinit var binding : ActivityNuevoUsuarioBinding
    private val REQUEST_CODE_TAKE_FOTO = 300
    private val REQUEST_CODE_GALERY = 400
    private val REQUEST_SAVE_CODE_GALERY = 100

    private var mFotoSeleccionadaURI: Uri? = null
    lateinit var conexion: UsuarioDB
    var foto = false

    lateinit var currentsPhotoPath: String
    lateinit var file: File
    lateinit var imagen:File
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = UsuarioDB(this)


        binding.imagen.setOnClickListener() {
            seleccionarGaleria();
        }

        binding.btnFoto.setOnClickListener(){
            checkPermissionCamera()
        }

        binding.guardarbtn.setOnClickListener {
            val nombre = binding.nombreEt.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val usuarios = conexion.listaTodos()
                    if (usuarios.isNotEmpty()){
                        if(foto== false){
                            conexion.insertar_datos(usuarios.last().id + 1 ,nombre, 0, null)
                        }else{
                            conexion.insertar_datos(usuarios.last().id + 1 ,nombre, 0, currentsPhotoPath)
                            foto=false

                        }

                    }else{
                        if(foto== false){
                            conexion.insertar_datos( 0  ,nombre, 0, null)
                        }else{
                            conexion.insertar_datos( 0  ,nombre, 0, currentsPhotoPath)
                            foto=false
                        }

                    }
                    startActivity(Intent(this@NuevoUsuario,SeleccionarUsuario::class.java))
                    this@NuevoUsuario.finish()
                }
        }
    }

    private fun checkPermissionCamera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
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
    private fun checkPermissionStorage() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    seleccionarGaleria();
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE_GALERY

                    )
                }
                }else{
                seleccionarGaleria();
                }
            }else{
            seleccionarGaleria();
            }
    }


    private fun sacaFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager)!= null){

            var photoFile : File? = null

            try{
                photoFile= createFile()
            }catch(e:IOException){
                e.printStackTrace()
            }
            if (photoFile!= null){
                var photoUri = FileProvider.getUriForFile(
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

    private fun seleccionarGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        foto = true
        startActivityForResult(intent, REQUEST_CODE_GALERY)
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
                seleccionarGaleria()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_TAKE_FOTO->{
                if(resultCode!= Activity.RESULT_OK ){
                    Toast.makeText(this,"No se ha sacado la foto", Toast.LENGTH_SHORT).show()
                }else{
                    binding.imagen.setImageURI(currentsPhotoPath.toUri())
                 }
            }
        REQUEST_CODE_GALERY->{
            if(resultCode!= Activity.RESULT_OK ){
                Toast.makeText(this,"No se ha sacado la foto", Toast.LENGTH_SHORT).show()
            }else{
                if(resultCode== Activity.RESULT_OK && data!=null){
                    mFotoSeleccionadaURI = data?.data

                    currentsPhotoPath = data.data.toString()
                    Toast.makeText(this,"${currentsPhotoPath}", Toast.LENGTH_SHORT).show()

                    binding.imagen.setImageURI(mFotoSeleccionadaURI)
                    Toast.makeText(this,"Galeria", Toast.LENGTH_SHORT).show()

                }
            }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)

    }
}