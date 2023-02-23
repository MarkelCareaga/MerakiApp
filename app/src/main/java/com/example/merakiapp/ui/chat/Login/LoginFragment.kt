package com.example.merakiapp.ui.chat.Login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentLoginBinding

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
        if (activity?.getSharedPreferences("datosUsuario", 0)?.getString("nombre", "") != "" ){
            findNavController().navigate(R.id.chatFragment)
        }
        return binding.root
    }

     override fun onViewCreated(view:View,savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         // Aqui se llama a la funcion cargarPreguntas
         viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

         binding.btnAceptar.setOnClickListener {
             // Almacenar datos para el Login
             val nombreUsuario = binding.textNombre.text

             // Comprobar si los datos se han introducido
             if (nombreUsuario.isNullOrEmpty()) {
                 Toast.makeText(this.requireContext(), "Error. Introduzca todos los datos necesarios.",
                     Toast.LENGTH_SHORT).show()
             } else {
                 activity?.getSharedPreferences("datosUsuario",0)!!.edit().putString("nombre", nombreUsuario.toString()).apply()
                 findNavController().navigate(R.id.chatFragment)
             }

         }
    }

}