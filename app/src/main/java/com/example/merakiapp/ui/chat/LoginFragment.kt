package com.example.merakiapp.ui.chat

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentLoginBinding
import com.example.merakiapp.servicios.socketChat

class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding


    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
/*
  findNavController().navigate(R.id.chatFragment)
 */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding= FragmentLoginBinding.inflate(layoutInflater)
        binding.acceptButton.setOnClickListener {
            val nombreUsuario=binding.inputName.text
            val intent=Intent( this.requireContext(), ChatFragment::class.java)
            intent.putExtra("nombreUsuario",nombreUsuario)
        }
        // TODO: Use the ViewModel
    }

}