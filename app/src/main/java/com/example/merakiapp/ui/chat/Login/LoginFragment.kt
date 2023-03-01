package com.example.merakiapp.ui.chat.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentLoginBinding
import com.example.merakiapp.servicios.ServicioChat

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el archivo de diseño y establece la variable _binding con la instancia de FragmentLoginBinding
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Comprueba si el usuario ha dejado una cuenta pendiente de cerrar sesión
        if (activity?.getSharedPreferences("datosUsuario", 0)?.getString("nombre", "") != "") {
            // En caso de tener una cuenta iniciada, se abrirá automáticamente el chat
            findNavController().navigate(R.id.chatFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAceptar.setOnClickListener {
            // Almacenar datos del Login
            val nombreUsuario = binding.textNombre.text

            // Comprueba si los datos se han introducido
            if (nombreUsuario.isNullOrEmpty()) {
                Toast.makeText(
                    this.requireContext(), getString(R.string.errorLoginChat),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Al iniciar sesión con una cuenta nueva, se almacenará de forma local
                activity?.getSharedPreferences("datosUsuario", 0)!!.edit()
                    .putString("nombre", nombreUsuario.toString()).apply()

                // Envia el nombre de la cuenta al servidor y abre el chat
                ServicioChat.usuario(nombreUsuario.toString())
                findNavController().navigate(R.id.chatFragment)
            }

        }
    }

}