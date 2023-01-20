package com.example.merakiapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.merakiapp.Dialogos.Companion.mensajePermisos
import com.example.merakiapp.Dialogos.Companion.permisoDenegado
import com.example.merakiapp.databinding.ActivityInicioBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.example.merakiapp.explicaciones.ExplicacionesActivity

class Inicio : AppCompatActivity(), OnMapReadyCallback, Dialogos, Explicaciones {
    var libre :Boolean = false
    lateinit var mapa :GoogleMap
    private lateinit var binding: ActivityInicioBinding
    @SuppressLint("CommitPrefEdits", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)

        // Binding
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -----> ???
        // Se obtiene un sharedPreferences llamado "Inico"
        val sharedPreferences = getSharedPreferences("Inico", 0)

        // ------------------------------- CONTROL DE BOTONES -------------------------------
        // MODO EXPLORADOR
        binding.btnExplorador.setOnClickListener {

            // Verifica si la aplicación tiene permisos para acceder a la ubicación del dispositivo
            if (ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )

            } else if (ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    this, android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Si no tiene permisos, solicita permisos para ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )
            }
        }

        // MODO LIBRE
        binding.btnLibre.setOnClickListener {
            // Si ha otorgado los permisos, activa la variable libre y guarda una preferencia en SharedPreferences
            libre = true
            val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",true).apply()
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

            // Inicia otra Activity
            val intent = Intent(this, MenuNav::class.java)
            startActivity(intent)
        }

        // APARTADO DE AYUDA
        binding.btnAyudaInicio.setOnClickListener {
            // INFO: Muestra el Dialog asociado a dicho apartado, especificando los siguientes campos:
            // Titulo de la parte superior del Dialog
            val titulo = Dialogos.tituloExplicacion
            // Contenido del Dialog
            val mensaje = Dialogos.mensajeInicio
            // Ejecuta la función proveniente de la Interfaz "Dialogos"
            mostrar_dialog(this, titulo, mensaje)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                // Si se reciben los permisos, muestra el mensaje
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Se guarda el valor false en el sharedPreferences "libre"
                    val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",false).apply()

                    // Se guarda el valor 0 en el sharedPreferences "PlayPause"
                    val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

                    // Se comprueba si ya existe una partida guardada
                    if ((this.getSharedPreferences("partida", 0)?.getBoolean("partida", false) == true)){
                        // Si existe, se inicia pantalla de codigo
                        pantallacodigo()
                    }else{
                        this.getSharedPreferences("partida",0).edit().putBoolean("partida",true).apply()
                        this.getSharedPreferences("validar1",0).edit().putBoolean("validar1",false).apply()
                        this.getSharedPreferences("validar2",0).edit().putBoolean("validar2",false).apply()
                        this.getSharedPreferences("validar3",0).edit().putBoolean("validar3",false).apply()
                        this.getSharedPreferences("validar4",0).edit().putBoolean("validar4",false).apply()
                        this.getSharedPreferences("validar5",0).edit().putBoolean("validar5",false).apply()
                        this.getSharedPreferences("validar6",0).edit().putBoolean("validar6",false).apply()
                        this.getSharedPreferences("validar7",0).edit().putBoolean("validar7",false).apply()

                        // Inicia la Activity de Explicaciones, especificando los recursos de dicho apartado
                        var intent_introduccion = abrirExplicacion(this, Recursos.pantalla_Introduccion,
                            Recursos.audio_Introduccion, Recursos.fondo_Introduccion)
                        startActivity(intent_introduccion)

                    }
                } else {
                    // Si no se reciben los permisos, muestra un mensaje de error
                    mostrar_dialog(this, permisoDenegado, mensajePermisos)
                }
                return
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
         TODO("Not yet implemented")
    }

    private fun pantallacodigo() {
        val layout = LinearLayout(this)
        layout.setPadding(20,10,20,10)
        layout.orientation = LinearLayout.VERTICAL
        val txtMensaje = TextView(this)
        txtMensaje.text= "¿Quieres empezar una nueva partida o continuar con la anterior?"
        layout.addView(txtMensaje)


        // Crea un cuadro de diálogo con un botón de "Nueva partida"
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder
            .setTitle("Se ha detectado una partida empezada")
            .setPositiveButton("Nueva partida",
                DialogInterface.OnClickListener { dialog, which ->
                    // Guarda una preferencia en SharedPreferences y inicia un intent para ir a otra actividad
                    val partida = this.getSharedPreferences("partida",0).edit().putBoolean("partida",true).apply()

                    this.getSharedPreferences("validar1",0).edit().putBoolean("validar1",false).apply()
                    this.getSharedPreferences("validar2",0).edit().putBoolean("validar2",false).apply()
                    this.getSharedPreferences("validar3",0).edit().putBoolean("validar3",false).apply()
                    this.getSharedPreferences("validar4",0).edit().putBoolean("validar4",false).apply()
                    this.getSharedPreferences("validar5",0).edit().putBoolean("validar5",false).apply()
                    this.getSharedPreferences("validar6",0).edit().putBoolean("validar6",false).apply()
                    this.getSharedPreferences("validar7",0).edit().putBoolean("validar7",false).apply()

                    // Inicia la Activity de Explicaciones, especificando los recursos de dicho apartado
                    var intent_introduccion = abrirExplicacion(this, Recursos.pantalla_Introduccion,
                        Recursos.audio_Introduccion, Recursos.fondo_Introduccion)
                    startActivity(intent_introduccion)
                })
            .setNegativeButton("Continuar",
                DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(this, MenuNav::class.java)
                    startActivity(intent)
                })
            .setOnDismissListener(){

            }


        val alertDialogPersonalizado: android.app.AlertDialog = builder.create()
        alertDialogPersonalizado.setView(layout);
        // Después mostrarla:
        alertDialogPersonalizado.show();
    }

}