package com.example.merakiapp.ui.chat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Carga el archivo de diseño y establece la variable _binding con la instancia de FragmentChatBinding
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation", "RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicia el servicio del chat
        val intent = Intent(this.requireContext(), ServicioChat::class.java)
        this.requireContext().startService(intent)

        // Al iniciar el chat, mostrará un dialog para introducir la sala deseada
        dialogoSala()

        // Se inicia la instancia del Broadcast
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(mMessageReceiver, IntentFilter("mensajes"))

        // Se recuperan los datos almacenados del usuario logeado
        val datosUsuario = activity?.getSharedPreferences("datosUsuario", 0)
        nombre = datosUsuario!!.getString("nombre", "").toString()
        sala = datosUsuario.getString("sala", "").toString()

        // Botón para cambiar la sala
        _binding!!.btnSala.setOnClickListener() {
            dialogoSala()
        }

        // Botón para detener el servicio del chat y volver a la pantalla del Login
        _binding!!.btnCerrar.setOnClickListener() {
            this.requireContext().stopService(intent)
            findNavController().navigate(R.id.nav_chat)
        }

        // Botón para enviar el mensaje
        _binding!!.btnEnviar.setOnClickListener() {
            // Comprueba si el mensaje enviado contiene datos
            if (_binding?.textMensaje?.text!!.isNotBlank()) {
                // Manda el mensaje por el servicio, junto con la fecha actual
                mensaje = binding.textMensaje.text.toString()
                val fecha = LocalDateTime.now().format(formatoData)
                println(fecha)
                ServicioChat.enviarmensaje(nombre, sala, mensaje, fecha)

                // Introduce el mensaje enviado al RecyclerView y oculta el teclado del móvil
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

    // Función para mostrar un dialog para introducir el nombre de una sala
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
                // Comprueba si se ha introducido la sala
                if (inputEditTextField.text.isBlank()) {
                    Toast.makeText(
                        this.requireContext(),
                        getString(R.string.errorSala),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Se procede a quitar los espacios en blanco y cambiar las letras a mayúsculas
                    val codigo = inputEditTextField.text.toString().quitarEspacios().uppercase()
                    _binding!!.chatTitulo.text = codigo

                    // Guarda la sala actual de manera local
                    activity?.getSharedPreferences("datosUsuario", 0)!!.edit()
                        .putString("sala", codigo).apply()
                    sala = activity?.getSharedPreferences("datosUsuario", 0)?.getString("sala", "")
                        .toString()
                    ServicioChat.sala(nombre, sala)
                    sleep(1000)
                    mensajesAdapter =
                        MensajeAdapter(ServicioChat.mensajes, ServicioChat.socketId, sala)
                    _binding!!.mensajesRecyclerView.adapter = mensajesAdapter

                    // En caso de que existan mensajes, se redirigirá al último
                    if (mensajesAdapter.itemCount > 0) {
                        _binding!!.mensajesRecyclerView.smoothScrollToPosition(mensajesAdapter.itemCount - 1)
                    }
                }
            }
            .setCancelable(false)
            .setNegativeButton(getString(R.string.cancelar)) { _, _ ->
            }
            .create()
        dialog.show()
    }

    // Función para quitar los espacios en blanco de la sala introducida
    private fun String.quitarEspacios(): String {
        return this.replace(" ", "")
    }

    // Comprobar si el Broadcast ha recibido algo desde el servicio del chat
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @SuppressLint("RestrictedApi")
        override fun onReceive(p0: Context?, p1: Intent?) {
            sleep(2000)
            mensajesAdapter = MensajeAdapter(ServicioChat.mensajes, ServicioChat.socketId, sala)
            _binding!!.mensajesRecyclerView.adapter = mensajesAdapter
            if (mensajesAdapter.itemCount > 0) {
                _binding!!.mensajesRecyclerView.smoothScrollToPosition(mensajesAdapter.itemCount - 1)
            }
            println(ServicioChat.mensajes)
        }
    }
}