package com.example.merakiapp.ui.ayuda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.merakiapp.databinding.FragmentAyudaBinding

class AyudaFragment : Fragment() {

    private var _binding: FragmentAyudaBinding? = null
    // Declara una variable "preguntas" de tipo List<Pregunta> que es una variable lateinit
    lateinit private var preguntas: List<Pregunta>
    // Declara una variable "PreguntasAdapter" de tipo PreguntasAdapter
    private lateinit var PreguntasAdapter :PreguntasAdapter

    // Esta propiedad solo es válida entre onCreateView y onDestroyView.
    private val binding get() = _binding!!

    // Carga el archivo de diseño asociado con este fragmento y devuelve la vista
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el archivo de diseño y establece la variable _binding con la instancia de FragmentAyudaBinding
        _binding = FragmentAyudaBinding.inflate(inflater, container, false)

        return binding.root
    }
    // este método se llama inmediatamente después de onCreateView
    override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aqui se llama a la funcion cargarPreguntas
        cargarPreguntas()
        PreguntasAdapter = PreguntasAdapter(preguntas)
        _binding?.RecyclerPreguntasReespuestas?.adapter = PreguntasAdapter

    }

    //este método se llama cuando la vista del fragmento ya no es necesaria
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    // este método se utiliza para cargar preguntas
    private fun cargarPreguntas (){
        // las preguntas se definen predefinidos aquí
        preguntas= listOf(
            Pregunta("¿Qué es Meraki?","Meraki es una aplicación que te permite " +
                    "disfrutar de una serie de juegos, mientrás exploras el pueblo de Bermeo."),
            Pregunta("¿Qué es el 'Modo Explorador'?","En el 'Modo Explorador' puedes" +
                    " acceder a cada juego solo si estás posicionado en su correspondiente ubicación, " +
                    "la cual se indica en el mapa."),
            Pregunta("¿Qué es el 'Modo Libre'?","En el 'Modo Libre', puedes acceder " +
                    "a todos los juego desde el principio, sin necesidad de estar posicionado en cada " +
                    "una de sus correspondientes ubicaciones.")
        )
    }
}