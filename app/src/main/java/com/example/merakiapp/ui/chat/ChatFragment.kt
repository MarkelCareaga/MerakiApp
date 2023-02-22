package com.example.merakiapp.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentChatBinding
import com.example.merakiapp.ui.chat.mensajes.MensajeAdapter
import com.example.merakiapp.ui.chat.mensajes.Mensajes
import java.util.*

class ChatFragment : Fragment() {
     private var _binding: FragmentChatBinding? = null
    // Declara una variable "preguntas" de tipo List<Pregunta> que es una variable lateinit

 // Declara una variable "preguntas" de tipo List<Pregunta> que es una variable lateinit
    lateinit private var mensajes: List<Mensajes>
    // Declara una variable "PreguntasAdapter" de tipo PreguntasAdapter
    private lateinit var mensajesAdapter :MensajeAdapter
    // Esta propiedad solo es válida entre onCreateView y onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: ChatViewModel

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el archivo de diseño y establece la variable _binding con la instancia de FragmentAyudaBinding
        _binding = FragmentChatBinding.inflate(inflater, container, false)
         cargarMensajes()
         mensajesAdapter = MensajeAdapter(mensajes)
        _binding?.mensajesRecyclerView?.adapter = mensajesAdapter
        return binding.root
    }

     override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aqui se llama a la funcion cargarPreguntas
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

    }

     private fun cargarMensajes(){
        // las preguntas se definen predefinidos aquí
        mensajes= listOf(
            Mensajes("0","Endika","123","Hola bjkgbj hoihlb hoihlkn lkhoihlk khoihkl ffefewf efewf ewfwe few few few few fewf", Date()),
            Mensajes("1","Markel","123","Hola", Date()),
        )
    }
}