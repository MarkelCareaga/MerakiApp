package com.example.merakiapp.sqLite

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.merakiapp.explicaciones.Explicaciones
import com.example.merakiapp.databinding.ActivitySeleccionarUsuarioBinding
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
import com.example.merakiapp.servicios.ServicioAudios
import com.google.android.material.internal.ContextUtils.getActivity

class SeleccionarUsuario : AppCompatActivity(), Explicaciones {
    private lateinit var conexion: UsuarioDB
    private lateinit var usuariosAdapter : ListaAdapter
    private lateinit var binding: ActivitySeleccionarUsuarioBinding

    private var listaDialogos = ListaDialogos()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deshabilitar rotación de pantalla (Landscape)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        conexion = UsuarioDB(this)
        val arrayList = ArrayList<Usuario>()
        val cursor = conexion.listaTodos()

        // VERIFIACAR SI LA LISTA DE LA BASE DE DATOS NO ESTA VACIA
        if (cursor.isNotEmpty()) {
            binding.lista.visibility= View.VISIBLE
            binding.txtInfo.visibility= View.GONE
            cursor.forEach {
                arrayList.add(it)
            }
            // Si no esta vacía, llamamos al adapter y lo lanzamos
            usuariosAdapter= ListaAdapter(arrayList, this, getActivity(this))
            binding.lista.adapter = usuariosAdapter
        } else {
            // Si esta vacia, mostramos un txt y ocultamos la lista
            binding.lista.visibility= View.GONE
            binding.txtInfo.visibility= View.VISIBLE
        }

        // ------------------ CONTROL DE BOTONES ------------------
        // AÑADIR
        binding.floatingAdd.setOnClickListener {
            val intent = Intent(this, NuevoUsuario::class.java)
            startActivity(intent)
            this.finish()
        }

        // AYUDA
        binding.btnAyudaSeleccionarUsuario.setOnClickListener {
            val mensaje = ListaRecursos.mensajeSeleccionarUsuario
            listaDialogos.mostrar_dialog(this, ListaRecursos.tituloExplicacion, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaSeleccionarUsuario.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(this, false)
        }

        // VOLVER
        binding.btnVolverSeleccionarUsuario.setOnClickListener {
            finish()

            intent = abrirExplicacion(this, ListaRecursos.pantalla_Izaro)
            startActivity(intent)
        }



    }
    override fun onBackPressed() {
        // Detiene el audio que se está reproduciendo
        var intent = Intent(this, ServicioAudios::class.java)
        stopService(intent)
        finish()

        intent = abrirExplicacion(this, ListaRecursos.pantalla_Izaro)
        startActivity(intent)
    }
}