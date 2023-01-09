package com.example.merakiapp.ui.ayuda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.merakiapp.R
import com.example.merakiapp.databinding.ItemPreguntaBinding

class PreguntasAdapter(private val preguntas:List<Pregunta>) : RecyclerView.Adapter<PreguntasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_pregunta,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(preguntas[position])
    }

    override fun getItemCount(): Int = preguntas.size

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val binding=ItemPreguntaBinding.bind(view)
        fun bind(pregunta: Pregunta) {
                binding.Pregunta.text=pregunta.Pregunta
                binding.Respuesta.text=pregunta.Respuesta
        }
    }


}