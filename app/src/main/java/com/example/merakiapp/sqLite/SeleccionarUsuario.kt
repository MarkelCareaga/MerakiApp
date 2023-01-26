package com.example.merakiapp.sqLite

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.merakiapp.Dialogos
import com.example.merakiapp.Explicaciones
import com.example.merakiapp.Recursos
import com.example.merakiapp.databinding.ActivitySeleccionarUsuarioBinding

class SeleccionarUsuario : AppCompatActivity(), Dialogos, Explicaciones {
    lateinit var conexion: UsuarioDB
    private lateinit var UsuariosAdapter : ListaAdapter
    private lateinit var binding: ActivitySeleccionarUsuarioBinding
    private val REQUEST_CODE_GALERY = 400


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySeleccionarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)



        conexion = UsuarioDB(this)
        val arrayList = ArrayList<Usuario>()
        val cursor = conexion.listaTodos()

        //VERIFIACAR SI LA LISTA DE LA BASE DE DATOS NO ESTA VACIA
        if (cursor.isNotEmpty()) {
            binding.lista.visibility= View.VISIBLE
            binding.txtInfo.visibility= View.GONE
            cursor.forEach {
                arrayList.add(it)
            }
            //si no esta vacia
            //llamamos al adapter y lo lanzamos
            UsuariosAdapter= ListaAdapter(arrayList, this)
            binding.lista.adapter = UsuariosAdapter
        } else {
            //si esta vacia
            //Mostramos un txt y ocultamos la lista
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
            val mensaje = Dialogos.mensajeSeleccionarUsuario
            mostrar_dialog(this, Dialogos.tituloExplicacion, mensaje)
        }

        // INFO ROTACIÓN
        binding.btnInfoPantallaSeleccionarUsuario.setOnClickListener {
            mostrar_info_pantalla(this, false)
        }

        // VOLVER
        binding.btnVolverSeleccionarUsuario.setOnClickListener {
            finish()

            intent = abrirExplicacion(this, Recursos.pantalla_Izaro,
                Recursos.audio_Izaro, Recursos.fondo_Izaro)
            startActivity(intent)
        }

    }
}