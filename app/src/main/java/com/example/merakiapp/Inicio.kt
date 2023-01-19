package com.example.merakiapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.merakiapp.databinding.ActivityInicioBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.example.merakiapp.explicaciones.DemoActivity
import com.example.merakiapp.explicaciones.ExplicacionesActivity

class Inicio : AppCompatActivity(), OnMapReadyCallback {
    var libre :Boolean = false
    lateinit var mapa :GoogleMap
    private lateinit var binding: ActivityInicioBinding
    @SuppressLint("CommitPrefEdits", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)

        binding=ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // se obtiene un sharedPreferences llamado "Inico"
        val sharedPreferences = getSharedPreferences("Inico", 0)

        // se establece un listener al boton btnDemo
//        binding.btnDemo!!.setOnClickListener {
//            // se guarda el valor false en el sharedPreferences "libre"
//            val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",false).apply()
//            // se guarda el valor 0 en el sharedPreferences "PlayPause"
//            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()
//
//            // se inicia la actividad DemoActivity
//            startActivity(Intent(this, DemoActivity::class.java))
//        }
        // se establece un listener al boton btnExplorador
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

        binding.btnLibre.setOnClickListener {
            // Si ha otorgado los permisos, activa la variable libre y guarda una preferencia en SharedPreferences
            libre = true
            val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",true).apply()
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

            // Inicia un intent para ir a otra actividad
            val intent = Intent(this, MenuNav::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                // Si se reciben los permisos, muestra el mensaje
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // se guarda el valor false en el sharedPreferences "libre"
                    val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",false).apply()
                    // se guarda el valor 0 en el sharedPreferences "PlayPause"
                    val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

                    // se comprueba si ya existe una partida guardada
                    if ((this.getSharedPreferences("partida", 0)?.getBoolean("partida", false) == true)){
                        // si existe se inicia pantalla de codigo
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

                        val textoSeleccionado = "Hola! Nosotros somos Patxi y Miren, los protagonistas y los guías " +
                                "de esta aplicación. Pertenecemos a una familia de marineros de Bermeo y seremos " +
                                "quienes os darán todas las explicaciones necesarias para poder realizar correctamente " +
                                "las actividades.Hola! Nosotros somos Patxi y Miren, los protagonistas y los guías de " +
                                "esta aplicación. Pertenecemos a una familia de marineros de Bermeo y seremos quienes " +
                                "os darán todas las explicaciones necesarias para poder realizar correctamente las actividades."
                        val intent = Intent(this, ExplicacionesActivity::class.java)
                            // Añadir datos referentes a la ventana de Introducción
                            .putExtra("pantallaSeleccionada", "introduccion")
                            .putExtra("audioSeleccionado", R.raw.audiointro)
                            .putExtra("fondoSeleccionado", R.drawable.fondoprincipiofinal)
                            .putExtra("textoSeleccionado", textoSeleccionado)
                        startActivity(intent)
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
                    val textoSeleccionado = "Hola! Nosotros somos Patxi y Miren, los protagonistas y los guías " +
                            "de esta aplicación. Pertenecemos a una familia de marineros de Bermeo y seremos " +
                            "quienes os darán todas las explicaciones necesarias para poder realizar correctamente " +
                            "las actividades.Hola! Nosotros somos Patxi y Miren, los protagonistas y los guías de " +
                            "esta aplicación. Pertenecemos a una familia de marineros de Bermeo y seremos quienes " +
                            "os darán todas las explicaciones necesarias para poder realizar correctamente las actividades."
                    val intent = Intent(this, ExplicacionesActivity::class.java)
                        // Añadir datos referentes a la ventana de Introducción
                        .putExtra("pantallaSeleccionada", "introduccion")
                        .putExtra("audioSeleccionado", R.raw.audiointro)
                        .putExtra("fondoSeleccionado", R.drawable.fondoprincipiofinal)
                        .putExtra("textoSeleccionado", textoSeleccionado)
                    startActivity(intent)
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
        // después mostrarla:
        alertDialogPersonalizado.show();
    }

}