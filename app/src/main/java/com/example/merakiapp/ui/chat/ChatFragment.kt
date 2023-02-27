package com.example.merakiapp.ui.chat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentChatBinding
import com.example.merakiapp.servicios.ServicioChat
import com.example.merakiapp.ui.chat.mensajes.MensajeAdapter
import com.google.android.material.internal.ViewUtils.hideKeyboard
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatFragment : Fragment() {
    companion object {
        lateinit var sala: String
    }

    private var _binding: FragmentChatBinding? = null

    // Declara una variable "PreguntasAdapter" de tipo PreguntasAdapter
    private lateinit var mensajesAdapter: MensajeAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    private var formatoData = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    // Esta propiedad solo es válida entre onCreateView y onDestroyView.
    private val binding get() = _binding!!

    // Datos del usuario
    private lateinit var nombre: String
    private lateinit var mensaje: String
    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el archivo de diseño y establece la variable _binding con la instancia de FragmentAyudaBinding
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation", "RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aqui se llama a la funcion cargarPreguntas
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val intent = Intent(this.requireContext(), ServicioChat::class.java)
        this.requireContext().startService(intent)

        dialogoSala()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver, IntentFilter("mensajes"))

        val datosUsuario = activity?.getSharedPreferences("datosUsuario", 0)
        nombre = datosUsuario!!.getString("nombre", "").toString()
        sala = datosUsuario.getString("sala", "").toString()

        _binding!!.btnSala.setOnClickListener() {
            dialogoSala()
        }

        _binding!!.btnCerrar.setOnClickListener() {
            this.requireContext().stopService(intent)
            findNavController().navigate(R.id.nav_chat)
        }

        _binding!!.btnEnviar.setOnClickListener() {
            if (_binding?.textMensaje?.text!!.isNotBlank()) {
                /*
                Manda el mensaje por el servicio
                 */
                mensaje = binding.textMensaje.text.toString()
                val fecha = LocalDateTime.now().format(formatoData)
                println(fecha)
                ServicioChat.enviarmensaje(nombre, sala, mensaje, fecha)
                sleep(1000)
                mensajesAdapter = MensajeAdapter(ServicioChat.mensajes, ServicioChat.socketId, sala)
                _binding!!.mensajesRecyclerView.adapter = mensajesAdapter
                _binding!!.mensajesRecyclerView.smoothScrollToPosition(mensajesAdapter.itemCount - 1)
                hideKeyboard(requireView())
            } else {
                Toast.makeText(
                    this.requireContext(),
                    getString(R.string.errorEnviarMensaje),
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.textMensaje.setText("")
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun dialogoSala() {
        val title = getString(R.string.sala)
        val mensajeServer = getString(R.string.infoBorrarMensajes)

        val message = "${getString(R.string.errorSala)} \n${mensajeServer}"
        val inputEditTextField = EditText(requireActivity())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setView(inputEditTextField)
            .setPositiveButton(getString(R.string.btn_aceptar)) { _, _ ->
                if (inputEditTextField.text.isBlank()) {
                    Toast.makeText(
                        this.requireContext(),
                        getString(R.string.errorSala),
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    val codigo = inputEditTextField.text.toString().quitarEspacios().uppercase()
                    _binding!!.chatTitulo.text = codigo
                    activity?.getSharedPreferences("datosUsuario", 0)!!.edit()
                        .putString("sala", codigo).apply()
                    sala = activity?.getSharedPreferences("datosUsuario", 0)?.getString("sala", "")
                        .toString()
                    ServicioChat.sala(nombre, sala)
                    sleep(1000)
                    mensajesAdapter =
                        MensajeAdapter(ServicioChat.mensajes, ServicioChat.socketId, sala)
                    _binding!!.mensajesRecyclerView.adapter = mensajesAdapter
                }
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
        dialog.show()
    }

    private fun String.quitarEspacios(): String {
        return this.replace(" ", "")
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("RestrictedApi")
        override fun onReceive(p0: Context?, p1: Intent?) {
            sleep(2000)
            mensajesAdapter = MensajeAdapter(ServicioChat.mensajes, ServicioChat.socketId, sala)
            _binding!!.mensajesRecyclerView.adapter = mensajesAdapter
            println(ServicioChat.mensajes)
        }
    }
}