package com.example.merakiapp.sqLite

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.merakiapp.databinding.ActivitySeleccionarUsuarioBinding

class SeleccionarUsuario : AppCompatActivity(){
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