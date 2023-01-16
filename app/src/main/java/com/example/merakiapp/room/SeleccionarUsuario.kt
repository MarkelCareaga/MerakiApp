package com.example.merakiapp.room

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ActivitySeleccionarUsuarioBinding
import com.example.merakiapp.databinding.ItemProductoUsuarioBinding

class SeleccionarUsuario : AppCompatActivity(){
    lateinit var conexion: UsuarioDB
    private lateinit var UsuariosAdapter : ListaAdapter
    private lateinit var binding: ActivitySeleccionarUsuarioBinding
    private val REQUEST_CODE_GALERY = 400


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySeleccionarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_MEDIA_LOCATION) == PackageManager.PERMISSION_GRANTED
                ) {

                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION),
                        REQUEST_CODE_GALERY

                    )
                }
            }

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