package com.example.merakiapp.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ActivitySeleccionarUsuarioBinding
import com.example.merakiapp.databinding.ItemProductoUsuarioBinding

class SeleccionarUsuario : AppCompatActivity(){
    lateinit var conexion: UsuarioDB
    private lateinit var UsuariosAdapter : ListaAdapter
    private lateinit var binding: ActivitySeleccionarUsuarioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySeleccionarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = UsuarioDB(this)
        val arrayList = ArrayList<Usuario>()
        val cursor = conexion.listaTodos()

        if (cursor.isNotEmpty()){
            binding.lista.visibility= View.VISIBLE
            binding.txtInfo.visibility= View.GONE
            cursor.forEach {
                arrayList.add(it)
            }
            UsuariosAdapter= ListaAdapter(arrayList, this)
            binding.lista.adapter = UsuariosAdapter
        }else{
            binding.lista.visibility= View.GONE
            binding.txtInfo.visibility= View.VISIBLE
        }


        binding.floatingAdd.setOnClickListener {
            val intent = Intent(this, NuevoUsuario::class.java)
            startActivity(intent)
            this.finish()
        }

    }
}