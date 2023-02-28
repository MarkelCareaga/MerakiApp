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
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.servicios.ServicioAudios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.util.*

class NuevoUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityNuevoUsuarioBinding
    private val REQUEST_CODE_TAKE_FOTO = 300
    private val REQUEST_CODE_GALERY = 400

    lateinit var conexion: UsuarioDB
    var foto = false

    lateinit var currentsPhotoPath: String
    lateinit var imageUri: Uri
    lateinit var file: File
    lateinit var imagen: File


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // Conectar con SQLITE
        conexion = UsuarioDB(this)

        binding.imagen.setOnClickListener() {
            // pedir y revisar permisos de galeria
            checkPermissionStorage()
        }

        binding.btnFoto.setOnClickListener() {
            // pedir y revisar permisos de camara
            checkPermissionCamera()
        }

        binding.guardarbtn.setOnClickListener {
            // Comprobar el txt de nombre
            if (binding.nombreEt.text.isNotBlank()) {
                // Si el txt no esta vacio
                // Cogemos el valor del txt
                val nombre = binding.nombreEt.text.toString()
                // Iniciamos una corutina para que no se salte ningun paso y siga el orden
                CoroutineScope(Dispatchers.IO).launch {
                    // llamamos a la funcion para listar todos los usuario de SQLITE
                    val usuarios = conexion.listaTodos()
                    // Comprobar si el resultado de la lista tiene o no usuarios
                    if (usuarios.isNotEmpty()) {
                        // si tiene usuarios
                        // comprobar si tenemos foto de camara o seleccionada
                        if (foto == false) {
                            /* si no tenemos foto
                             *
                             *  añadimos usuario con la foto en null a la base de datos
                             *  cogemos el valor id del ultimo usuario y le sumamos uno
                             *
                              */
                            conexion.insertar_datos(usuarios.last().id + 1, nombre, 0, null)
                        } else {
                            /* si tenemos foto
                            *
                            *  añadimos usuario con la foto a la base de datos
                            *  cogemos el valor id del ultimo usuario y le sumamos uno
                            *
                            * */
                            conexion.insertar_datos(
                                usuarios.last().id + 1,
                                nombre,
                                0,
                                currentsPhotoPath
                            )
                            //volvemos a dar el valor false a foto
                            foto = false
                        }
                    } else {
                        // si la lista de ususarios esta vacia
                        // comprobar si tenemos foto de camara o seleccionada

                        if (foto == false) {
                            // si no tenemos foto
                            // añadimos usuario pero sin foto puede ser null -> añadir a la base de datos
                            // como esta vacia el valor id es 0
                            conexion.insertar_datos(0, nombre, 0, null)
                        } else {
                            // si tenemos foto
                            // añadimos usuario pero con foto a la base de datos
                            // como esta vacia el valor id es 0
                            conexion.insertar_datos(0, nombre, 0, currentsPhotoPath)
                            //volvemos a dar el valor false a foto
                            foto = false
                        }

                    }
                    /*
                        al finalzar la corutina nos vamos al activity de seleccionar el
                        jugador y veremos el nuevo usuario
                    */
                    startActivity(Intent(this@NuevoUsuario, SeleccionarUsuario::class.java))
                    // finaalizamos este activity para que no quede abierta
                    this@NuevoUsuario.finish()
                }

            } else {
                // mensaje de para que introduzas un nombre al usuario
                Toast.makeText(this, getString(R.string.nombreUsuarioError), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    //Revisar permisos de camara
    private fun checkPermissionCamera() {
        // revisar si la version de movil es igual o mayor al andorid 9
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // revisar si la version de movil es igual o mayor al andorid 10
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Revisar si los permisos estan concedidos
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // si los permisos están concedidos
                    // se llama a la funcio de camara
                    sacaFoto();
                } else {
                    // si los permisos no están concedidos
                    // se pìden los permisos
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CODE_TAKE_FOTO
                    )
                }
            } else {
                // Revisar si los permisos estan concedidos
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // si los permisos están concedidos
                    // se llama a la funcio de camara
                    sacaFoto();
                } else {
                    // si los permisos no están concedidos
                    // se pìden los permisos
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CODE_TAKE_FOTO
                    )
                }
            }
        }
    }

    //Revisar permisos de galeria
    private fun checkPermissionStorage() {
        // revisar si la version de movil es igual o mayor al andorid 10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Revisar si los permisos estan concedidos

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_MEDIA_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // si los permisos están concedidos
                // se llama a la galeria
                galeria()
            } else {
                // si los permisos no están concedidos
                // se pìden los permisos
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION),
                    REQUEST_CODE_GALERY

                )
            }
            // revisar si la version de movil no es igual o mayor al andorid 10
        } else {
            // revisar si la version de movil es igual o mayor al andorid 9
            // en andorid 9 no hace falta pedir permisos de galeria
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // se llama a la galeria
                galeria();
            }
        }
    }

    //abrir galeria
    private fun galeria() {
        //llamamos al controlador donde esta el metodo de abrir galeria
        ImageController.selectPhotoFromGallery(this, REQUEST_CODE_GALERY)
        // dar valor a foto de true
        foto = true
    }

    // Abrir camara y guardar foto en el movil
    private fun sacaFoto() {
        // llamamos a la camara
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // comprobamos el valor de la accion no sea null
        if (intent.resolveActivity(packageManager) != null) {
            // creamos un file
            var photoFile: File? = null

            try {
                //llamamos a la funcion de crear archivo
                photoFile = createFile()
            } catch (e: IOException) {
                // por si da error al crear el archivo
                e.printStackTrace()
            }
            // comprobar si el archivo no es nulo
            if (photoFile != null) {
                // si no es nulo
                // decimos los permisos de proveedor y donde vamos a guardar el FILE
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "com.example.merakiapp",
                    photoFile
                )
                // Seleccioanr imagen guardada
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                // dar valor a foto de true
                foto = true
                // llamar a la funcion para obtener el resultado de la accion
                startActivityForResult(intent, REQUEST_CODE_TAKE_FOTO)
            }
        }
    }

    // funcion para crear la imagen de la Camara
    private fun createFile(): File {

        // Tiempo del sistema de cuando se saca la foto
        val timestamp = SimpleDateFormat("yyyyMMdd_HH-mm-ss", Locale.getDefault()).format(Date())
        //nombre del archivo
        val imgFilename = "IMG_" + timestamp
        // donde se va a ubicar
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // crear el archivo con el nombre, la direccion y de que tipo va a ser el archivo
        imagen = File.createTempFile(
            imgFilename,
            ".jpg",
            storageDir
        )
        // recoger la ruta de la imagen
        currentsPhotoPath = imagen.absolutePath
        //devolver la imagen
        return imagen
    }


    // respuesta a los permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        // comprobar si la variable de respuesta es la misma a la definida y enviada al ejecutar la camara
        if (requestCode == REQUEST_CODE_TAKE_FOTO) {
            // verificar si tiene los permisos de la camara
            if (permissions.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // llamar a la funcion de foto
                sacaFoto()
            }
        }
        // comprobar si la variable de respuesta es la misma a la definida y enviada al ejecutar la galeria
        else if (requestCode == REQUEST_CODE_GALERY) {
            // verificar si tiene los permisos de la galeria
            if (permissions.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // llamar a la funcion de galeria
                galeria()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    @Deprecated("Deprecated in Java")

    // Resultado de las acciones y permisos
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // varia el resultaddo dependiendpo de accion
        when (requestCode) {
            // Foto
            REQUEST_CODE_TAKE_FOTO -> {
                //comprobrobar si no se a cancelado
                if (resultCode != Activity.RESULT_OK) {
                    // si cancela la accion
                    Toast.makeText(this, getString(R.string.foto), Toast.LENGTH_SHORT).show()
                } else {
                    // si da el ok a la accion
                    binding.imagen.setImageURI(currentsPhotoPath.toUri())
                }
            }
            // Galeria
            REQUEST_CODE_GALERY -> {
                //comprobrobar si no se a cancelado
                if (resultCode != Activity.RESULT_OK) {
                    // si cancela la accion
                    Toast.makeText(this, getString(R.string.galeria), Toast.LENGTH_SHORT).show()
                } else {
                    // si da el ok a la accion y no es nulo
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        // le damos el valor de la foto seleccionada  a imagenUri
                        imageUri = data.data!!
                        imageUri.let {
                            // llamamos a la base de datos para listar los usuarios
                            val usuarios = conexion.listaTodos()
                            // verificar si la lista no esta vacia
                            if (usuarios.isNotEmpty()) {
                                // si no esta vacia
                                // llamamos al controlador de imagenes de galeria y llamamos para guardar la imagen
                                //cogemos el valor id del ultimo usuario y le sumamos uno
                                ImageController.saveImage(
                                    this@NuevoUsuario,
                                    usuarios.last().id + 1,
                                    it
                                )
                                // llamamos a la funcion para recuperar la imagen
                                currentsPhotoPath = ImageController.getImageUri(
                                    this@NuevoUsuario,
                                    usuarios.last().id + 1
                                )
                                    .toString()


                            } else {
                                // si esta vacia
                                // llamamos al controlador de imagenes de galeria y llamamos para guardar la imagen
                                // como esta vacia el valor id es 0
                                ImageController.saveImage(this@NuevoUsuario, 0, it)
                                // llamamos a la funcion para recuperar la imagen
                                currentsPhotoPath =
                                    ImageController.getImageUri(this@NuevoUsuario, 0)
                                        .toString()
                            }
                        }
                        // insertamos la imagen guardada al ImageView
                        binding.imagen.setImageURI(imageUri)
                    }
                }
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onBackPressed() {
        // Detiene el audio que se está reproduciendo
        finish()

        var intent = Intent(this, SeleccionarUsuario::class.java)
        startActivity(intent)
    }
}