package com.example.merakiapp.ui.acercade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentAcercaDeBinding

class AcercadeFragment : Fragment() {

    private var _binding: FragmentAcercaDeBinding? = null

    // Propiedad calculada para acceder a _binding de manera segura, solo es valida entre onCreateView y onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Crea una instancia de la clase ViewModel

        // Infla el diseño y enlaza con la variable _binding
        _binding = FragmentAcercaDeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtiene una referencia al TextView textGalleryUpv del diseño
        val textView_upv: TextView = binding.textGalleryUpv
        textView_upv.text = getString(R.string.texto_upv)

        // Obtiene una referencia al TextView textGalleryGrupo3 del diseño
        val textView_grupo3: TextView = binding.textGalleryGrupo3
        textView_grupo3.text = getString(R.string.texto_grupo3)

        // Obtiene una referencia al TextView textView_copyright del diseño
        val textView_copyright: TextView = binding.textGalleryCopyright
        textView_copyright.text = getString(R.string.texto_copyright)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}