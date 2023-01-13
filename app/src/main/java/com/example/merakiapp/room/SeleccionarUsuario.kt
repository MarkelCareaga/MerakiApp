package com.example.merakiapp.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ActivitySeleccionarUsuarioBinding
import com.example.merakiapp.juegos.IslaIzaroActivity
import com.example.merakiapp.ui.ayuda.Pregunta
import com.example.merakiapp.ui.ayuda.PreguntasAdapter

class SeleccionarUsuario : AppCompatActivity(){
    // Declara una variable "preguntas" de tipo List<Pregunta> que es una variable lateinit
    // Declara una variable "PreguntasAdapter" de tipo PreguntasAdapter
    private lateinit var UsuariosAdapter : ListaAdapter
    private lateinit var binding: ActivitySeleccionarUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySeleccionarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = DatabaseRoomApp.database.usuarioDao.TodosUsuarios()
        UsuariosAdapter= ListaAdapter(user)
        binding?.lista?.adapter = UsuariosAdapter

        binding.floatingAdd.setOnClickListener {
            val intent = Intent(this, NuevoUsuario::class.java)
            startActivity(intent)
        }

    }

   /* override fun onItemJugar(item: Usuario) {
        val intent = Intent(this, IslaIzaroActivity::class.java)
            .putExtra("Usuario", item)
        startActivity(intent)

    }*/
}