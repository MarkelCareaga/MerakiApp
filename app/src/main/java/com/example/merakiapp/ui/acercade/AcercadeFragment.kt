package com.example.merakiapp.ui.acercade

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.merakiapp.databinding.FragmentAcercaDeBinding


class AcercadeFragment : Fragment() {

    private var _binding: FragmentAcercaDeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(AcercadeViewModel::class.java)

        _binding = FragmentAcercaDeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView_upv: TextView = binding.textGalleryUpv
        galleryViewModel.texto_upv.observe(viewLifecycleOwner) {
            textView_upv.text = it
        }

        val textView_grupo3: TextView = binding.textGalleryGrupo3
        galleryViewModel.texto_grupo3.observe(viewLifecycleOwner) {
            textView_grupo3.text = it
        }

        val textView_copyright: TextView = binding.textGalleryCopyright
        galleryViewModel.texto_copyright.observe(viewLifecycleOwner) {
            textView_copyright.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}