package com.example.merakiapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.example.merakiapp.databinding.ActivityInicioBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.example.merakiapp.explicaciones.DemoActivity
import com.example.merakiapp.explicaciones.ExplicacionesActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.Console

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


        val sharedPreferences = getSharedPreferences("Inico", 0)

        binding.btnDemo.setOnClickListener {
            val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",false).apply()
            val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

            startActivity(Intent(this, DemoActivity::class.java))
        }
        binding.btnExplorador.setOnClickListener {
            if ((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
                mostrar_dialog(this, permisoDenegado, mensajePermisos)
            else{
                val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",false).apply()
                val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

                if ((this.getSharedPreferences("partida", 0)?.getBoolean("partida", false) == true)){
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
            }


        }

        binding.btnLibre.setOnClickListener {
            if ((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
                mostrar_dialog(this, permisoDenegado, mensajePermisos)
            else{
                libre = true
                val libre = this.getSharedPreferences("pref",0).edit().putBoolean("libre",true).apply()
                val PlayPause = this.getSharedPreferences("pref",0).edit().putInt("PlayPause",0).apply()

                val intent = Intent(this, MenuNav::class.java)
                startActivity(intent)
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



        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder
            .setTitle("Se ha detectado una partida empezada")
            .setPositiveButton("Nueva partida",
                DialogInterface.OnClickListener { dialog, which ->

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