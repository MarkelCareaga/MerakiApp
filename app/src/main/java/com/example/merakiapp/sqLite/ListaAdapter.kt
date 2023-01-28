package com.example.merakiapp.sqLite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ItemProductoUsuarioBinding
import com.example.merakiapp.juegos.IslaIzaroActivity

class ListaAdapter(val arrayList: ArrayList<Usuario>, val contexta: Context, val activity: Activity) : RecyclerView.Adapter<ListaAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // creamos la vista con el xml item_producto_usuario
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_usuario, parent, false)
        //llamamos a la vista
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //le decimos que valores se va a componer la vista
        // creamos un metodo para dar los valores a cada a item
        // le pasamos un arraylist y la recorremos por posicion y tambien se le pasa un contexto
        holder.bind(arrayList[position], contexta,activity)

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        // definimos el binding del los items
        val binding = ItemProductoUsuarioBinding.bind(view)
        //metodo  para dar valores a los items
        fun bind(usuario: Usuario, contexta: Context,activity: Activity) {
            // llamamos a los valores dentro del binding
            with(binding) {
                // le da damos el valor del txt nombreUsuario el nombre del usaurio
                nombreUsuario.text = usuario.nombreusuario
                // creamos un valor imagen que se le pasa la imagen del usaurio
                val imagen = usuario.imagen?.toUri()
                // comprobamos si la imagen del usuario no es nula
                if (usuario.imagen != null) {
                    // si no es nula se le agrega a imagenUsuario la imagen obtenida arriba
                    imagenUsuario.setImageURI(imagen)
                }
                //----------------------Boton Play------------------------//
                buttonPlay.setOnClickListener {
                    // al dar el boton play se le lleva al juego IslaIzaroActivity y cierra este activity

                    val intent = Intent(contexta,IslaIzaroActivity::class.java)
                        // le mandamos al activity todos los valores del usuario elegido
                        .putExtra("id", usuario.id)
                        .putExtra("name", usuario.nombreusuario)
                        .putExtra("pasos", usuario.pasosUsuario)
                        .putExtra("imagen", usuario.imagen)
                    // se ejecuta la accion de llevarle al juego
                    contexta.startActivity(intent)
                    activity.finish()
                }
            }
        }
    }
    // devuelve el tamaño del array
    override fun getItemCount(): Int = arrayList.size
}
