package com.example.merakiapp.ui.ayuda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.merakiapp.databinding.FragmentAyudaBinding

class AyudaFragment : Fragment() {

    private var _binding: FragmentAyudaBinding? = null
    lateinit private var preguntas: List<Pregunta>
    private lateinit var PreguntasAdapter :PreguntasAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAyudaBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarPreguntas()
        PreguntasAdapter = PreguntasAdapter(preguntas)
        _binding?.RecyclerPreguntasReespuestas?.adapter = PreguntasAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun cargarPreguntas (){
        preguntas= listOf(
            Pregunta("Que es Meraki?","Meraki es una App para entretener a niños"),
            Pregunta("Que es Meraki?","Meraki es una App para entretener a niños"),
            Pregunta("Que es Meraki?","Meraki es una App para entretener a niños"),
            Pregunta("Que es Meraki?","Meraki es una App para entretener a niños"),
        )
    }
}