package com.example.merakiapp.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ItemPreguntaBinding
import com.example.merakiapp.ui.ayuda.Pregunta
import com.example.merakiapp.ui.ayuda.PreguntasAdapter

class ListaAdapter(private val usuarios: List<Usuario>): RecyclerView.Adapter<ListaAdapter.ViewHolder>() {

    private class DiffCallback : DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_usuario, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(usuarios[position])
    }

   inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val tvNombre = itemView.findViewById(R.id.nombre_et) as TextView
        private val btnJugar = itemView.findViewById(R.id.button_play) as Button
        private val imagen = itemView.findViewById(R.id.imagenUsuario) as ImageView

       fun bind(item: Usuario) {
            tvNombre.text = item.nombreusuario

            btnJugar.setOnClickListener {
                println("Item")
            }
        }
    }

    interface OnItemClickListener {
        fun onItemJugar(item: Usuario)
        
    }

    override fun getItemCount(): Int = usuarios.size    }
