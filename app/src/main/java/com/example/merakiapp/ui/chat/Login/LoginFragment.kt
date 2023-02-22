package com.example.merakiapp.ui.chat.Login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentLoginBinding
import com.example.merakiapp.ui.chat.ChatFragment

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel
    private val binding get() = _binding!!
      override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el archivo de diseño y establece la variable _binding con la instancia de FragmentAyudaBinding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

     override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aqui se llama a la funcion cargarPreguntas
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
         _binding?.btnAceptar?.setOnClickListener {
             val nombreUsuario=binding.textNombre.text
              findNavController().navigate(R.id.chatFragment)

         }
    }


}