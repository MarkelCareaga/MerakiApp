package com.example.merakiapp.explicaciones

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.merakiapp.FinalActivity
import com.example.merakiapp.Inicio
import com.example.merakiapp.R
import com.example.merakiapp.abrirExplicacionTest
import com.example.merakiapp.databinding.ActivityDemoBinding

// ACTIVITY DE DEMOSTRACIÓN: LISTA DE ACCESOS DIRECTOS A EXPLICACIONES

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    private var audioSeleccionado = 0                   // Audio a reproducir en la siguiente Activity
    private var fondoSeleccionado = 0                   // Fondo a mostrar en la siguiente Activity
    private var textoSeleccionado = ""                  // Texto a mostrar en la siguiente Activity
    private lateinit var pantallaSeleccionada: String   // Pantalla enlazada al boton Siguiente del próximo Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CONTROL DE BOTONES
        // Introducción
        binding.btnDemoIntroduccion.setOnClickListener {
            pantallaSeleccionada = "introduccion"
            audioSeleccionado = R.raw.audiointro
            fondoSeleccionado = R.drawable.fondoprincipiofinal

            var intent_demo = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_demo)
        }

        // 1.- Puerta de San Juan
        binding.btnDemoPuertaSanJuan.setOnClickListener {
            pantallaSeleccionada = "puerta_de_san_juan"
            audioSeleccionado = R.raw.audiopuertadesanjuan
            fondoSeleccionado = R.drawable.fondopuertasanjuan

            var intent_puerta_san_juan = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_puerta_san_juan)
        }

        // 2.- Badatoz Estatua
        binding.btnDemoBadatozEstatua.setOnClickListener {
            pantallaSeleccionada = "badatoz_estatua"
            audioSeleccionado = R.raw.audiobadatoz
            fondoSeleccionado = R.drawable.fondobadatoz

            var intent_badatoz = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_badatoz)
        }

        // 3.- Feria del Pescado
        binding.btnDemoFeriaPescado.setOnClickListener {
            pantallaSeleccionada = "feria_del_pescado"
            audioSeleccionado = R.raw.audioferiadelpescado
            fondoSeleccionado = R.drawable.fondoferiapescado

            var intent_feria_pescado = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_feria_pescado)
        }

        // 4.- Olatua Estatua
        binding.btnDemoOlatuaEstatua.setOnClickListener {
            pantallaSeleccionada = "olatua_estatua"
            audioSeleccionado = R.raw.audioolatua
            fondoSeleccionado = R.drawable.fondoolatua

            var intent_olatua = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_olatua)
        }

        // 5.- Xixili
        binding.btnDemoXixili.setOnClickListener {
            pantallaSeleccionada = "xixili"
            audioSeleccionado = R.raw.audioxixili
            fondoSeleccionado = R.drawable.fondoxixili

            var intent_xixili = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_xixili)
        }

        // 6.- Isla de Izaro
        binding.btnDemoIslaIzaro.setOnClickListener {
            pantallaSeleccionada = "isla_de_izaro"
            audioSeleccionado = R.raw.audioisladeizaro
            fondoSeleccionado = R.drawable.fondoizaro1

            var intent_isla_izaro = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_isla_izaro)
        }

        // 7.- Gaztelugatxe
        binding.btnDemoGaztelugatxe.setOnClickListener {
            pantallaSeleccionada = "gaztelugatxe"
            audioSeleccionado = R.raw.audiosanjuandegaztelugatxe
            fondoSeleccionado = R.drawable.fondogaztelugatxe

            var intent_gaztelugatxe = abrirExplicacionTest(this, pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_gaztelugatxe)
        }

        // Final
        binding.btnAbrirFinal.setOnClickListener {
            startActivity(Intent(this, FinalActivity::class.java))
        }

        binding.btnVolverMapa.setOnClickListener {
            startActivity(Intent(this, Inicio::class.java))
        }
    }
}