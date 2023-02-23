package com.example.merakiapp.ui.chat.mensajes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ChatItemBinding
import com.example.merakiapp.servicios.ServicioChat

class MensajeAdapter(private val mensajes:List<Mensajes>) : RecyclerView.Adapter<MensajeAdapter.ViewHolder>() {

    // Encargado de crear el viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el archivo de diseño item_pregunta
        val view=LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_item,parent,false)
        // Devuelve una instancia de ViewHolder
        return ViewHolder(view)
    }

    // Encargado de vincular los datos de la pregunta en una posición específica con el ViewHolder correspondiente
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // llama al metodo bind del viewholder
        holder.bind(mensajes[position])
    }
    // Devuelve el número de elementos en la lista de preguntas
    override fun getItemCount(): Int = mensajes.size

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        // Variable de enlace del layout item_pregunta
        val binding =  ChatItemBinding.bind(view)
        // Metodo para establecer la pregunta y respuesta en las vistas correspondientes
        fun bind(mensaje: Mensajes) {
            if(mensaje.id == ServicioChat().socketId) {
                binding.const1!!.visibility = View.GONE
                binding.const2!!.visibility = View.VISIBLE

                binding.mensajeU!!.text = mensaje.mensaje
                binding.nombreU!!.text = mensaje.nombreUusuario


            } else{
                binding.const1!!.visibility = View.VISIBLE
                binding.const2!!.visibility = View.GONE

                binding.mensaje.text = mensaje.mensaje
                binding.nombreUsuario.text = mensaje.nombreUusuario
            }
        }
    }


}