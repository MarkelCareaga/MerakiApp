package com.example.merakiapp.room

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ActivityNuevoUsuarioBinding
import com.example.merakiapp.databinding.ActivitySeleccionarUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoUsuario : AppCompatActivity(){
    private lateinit var binding : ActivityNuevoUsuarioBinding
    private val REQUEST_CODE_TAKE_FOTO = 100
    private val FOTO = 101
    private val REQUEST_CODE_GALERY = 200

    private var mFotoSeleccionadaURI: Uri? = null
    private val okGaleria = 26
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.imagen.setOnClickListener() {
            seleccionarGaleria()
// Verifica si la aplicación tiene permisos para acceder a la ubicación del dispositivo
        }
        var id: Long? = null

        binding.btnFoto.setOnClickListener(){
            checkPermissionCamera()
        }


        binding.guardarbtn.setOnClickListener {
            val nombre = binding.nombreEt.text.toString()

            val usuario = Usuario(0 ,nombre, 0, R.drawable.dragon)

            if (id != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    usuario.id = id
                    DatabaseRoomApp.database.usuarioDao.modificarUsuario(usuario)


                    this@NuevoUsuario.finish()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    DatabaseRoomApp.database.usuarioDao.insertarUsuario(usuario)

                    this@NuevoUsuario.finish()
                }
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
                        arrayOf(Manifest.permission.CAMERA),
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
        if(intent.resolveActivity(packageManager)!=null) {
            startActivityForResult(intent, REQUEST_CODE_TAKE_FOTO)
        }
    }

    private fun seleccionarGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_GALERY)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
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
        if(requestCode== FOTO){
            if(resultCode== Activity.RESULT_OK && data!=null){
                data?.extras?.let{bundle ->
                    mFotoSeleccionadaURI = bundle.get("data") as Uri
                    binding.imagen.setImageURI(mFotoSeleccionadaURI)
                }
            }
        }else if(requestCode == REQUEST_CODE_GALERY){
            if(resultCode== Activity.RESULT_OK && data!=null){
                mFotoSeleccionadaURI = data?.data
                binding.imagen.setImageURI(mFotoSeleccionadaURI)

            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}