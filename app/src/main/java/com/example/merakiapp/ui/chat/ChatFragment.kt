package com.example.merakiapp.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
     private var _binding: FragmentChatBinding? = null
    // Declara una variable "preguntas" de tipo List<Pregunta> que es una variable lateinit


    // Esta propiedad solo es válida entre onCreateView y onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}