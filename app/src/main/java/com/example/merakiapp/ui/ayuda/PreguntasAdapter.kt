package com.example.merakiapp.ui.ayuda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ItemPreguntaBinding

class PreguntasAdapter(private val preguntas:List<Pregunta>) : RecyclerView.Adapter<PreguntasAdapter.ViewHolder>() {

    // Encargado de crear el viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el archivo de diseño item_pregunta
        val view=LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_pregunta,parent,false)
        // Devuelve una instancia de ViewHolder
        return ViewHolder(view)
    }

    // Encargado de vincular los datos de la pregunta en una posición específica con el ViewHolder correspondiente
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // llama al metodo bind del viewholder
        holder.bind(preguntas[position])
    }
    // Devuelve el número de elementos en la lista de preguntas
    override fun getItemCount(): Int = preguntas.size

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        // Variable de enlace del layout item_pregunta
        val binding=ItemPreguntaBinding.bind(view)
        // Metodo para establecer la pregunta y respuesta en las vistas correspondientes
        fun bind(pregunta: Pregunta) {
                binding.Pregunta.text=pregunta.Pregunta
                binding.Respuesta.text=pregunta.Respuesta
        }
    }


}