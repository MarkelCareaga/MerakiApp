package com.example.merakiapp.sqLite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ItemProductoUsuarioBinding
import com.example.merakiapp.juegos.IslaIzaroActivity

class ListaAdapter(val arrayList: ArrayList<Usuario>,val contexta:Context) : RecyclerView.Adapter<ListaAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_usuario, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayList[position], contexta)

    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemProductoUsuarioBinding.bind(view)

        fun bind(usuario: Usuario, contexta: Context) {
            with(binding) {

                nombreUsuario.setText(usuario.nombreusuario)
                val imagen = usuario.imagen?.toUri()
                if(usuario.imagen != null){
                    imagenUsuario.setImageURI(imagen)
                }
                buttonPlay.setOnClickListener {
                    val intent = Intent(contexta,IslaIzaroActivity::class.java)
                        .putExtra("id", usuario.id)
                        .putExtra("name", usuario.nombreusuario)
                        .putExtra("pasos", usuario.pasosUsuario)
                        .putExtra("imagen", usuario.imagen)
                    contexta.startActivity(intent);
                    Toast.makeText(contexta,"${usuario.id}, ${usuario.nombreusuario}, ${usuario.pasosUsuario}, ${usuario.imagen}", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    interface OnItemClickListener {
        fun onItemJugar(item: Usuario)

    }

    override fun getItemCount(): Int = arrayList.size
}
