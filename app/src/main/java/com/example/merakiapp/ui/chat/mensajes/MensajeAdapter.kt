package com.example.merakiapp.ui.chat.mensajes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ChatItemBinding

class MensajeAdapter(private val mensajes: List<Mensajes>, val socketid: String, val sala: String) :
    RecyclerView.Adapter<MensajeAdapter.ViewHolder>() {

    // Encargado de crear el viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el archivo de diseño chat_item
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_item, parent, false)
        // Devuelve una instancia de ViewHolder
        return ViewHolder(view)
    }

    // Encargado de vincular los datos del mensaje en una posición específica con el ViewHolder correspondiente
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // llama al metodo bind del viewholder
        holder.bind(mensajes[position], socketid, sala)
    }

    // Devuelve el número de elementos en la lista de mensajes
    override fun getItemCount(): Int = mensajes.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Variable de enlace del layout chat_item
        val binding = ChatItemBinding.bind(view)

        // Metodo para establecer el mensaje en las vistas correspondientes
        fun bind(mensaje: Mensajes, socketid: String, sala: String) {
            // Comprobar si el mensaje pertenece a la sala actual
            if (mensaje.sala == sala) {
                // Comprobar si el mensaje pertenece al usuario logeado
                if (mensaje.id == socketid) {
                    // Se oculta el item del mensaje de los usuarios secundarios
                    binding.const1!!.visibility = View.GONE
                    // Se muestra el item del mensaje del usuario actual
                    binding.const2!!.visibility = View.VISIBLE
                    binding.mensajeU!!.text = mensaje.mensaje
                } else {
                    // Se muestra el item del mensaje de los usuarios secundarios
                    binding.const1!!.visibility = View.VISIBLE
                    // Se oculta el item del mensaje del usuario actual
                    binding.const2!!.visibility = View.GONE
                    binding.mensaje.text = mensaje.mensaje
                    binding.nombreUsuario.text = mensaje.nombreUusuario
                }
            } else {
                binding.const1!!.visibility = View.GONE
                binding.const2!!.visibility = View.GONE
            }
        }
    }
}