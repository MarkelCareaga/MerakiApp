package com.example.merakiapp.ui.chat

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.merakiapp.R
import com.example.merakiapp.databinding.FragmentChatBinding
import com.example.merakiapp.servicios.ServicioAudios
import com.example.merakiapp.servicios.ServicioChat
import com.example.merakiapp.ui.chat.mensajes.MensajeAdapter
import com.example.merakiapp.ui.chat.mensajes.Mensajes
import io.socket.client.IO
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null

    // Declara una variable "preguntas" de tipo List<Pregunta> que es una variable lateinit
    private  var mensajes: MutableList<Mensajes> = mutableListOf()

    // Declara una variable "PreguntasAdapter" de tipo PreguntasAdapter
    private lateinit var mensajesAdapter: MensajeAdapter

    // Esta propiedad solo es válida entre onCreateView y onDestroyView.
    private val binding get() = _binding!!

    // Datos del usuario
    private lateinit var nombre: String
    private lateinit var sala: String
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aqui se llama a la funcion cargarPreguntas
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        val intent = Intent(this.requireContext(), ServicioChat::class.java)
        this.requireContext().startService(intent)
        dialogoSala()

        dialogoSala()

        cargarMensajes()

        val datosUsuario = activity?.getSharedPreferences("datosUsuario", 0)
        nombre = datosUsuario!!.getString("nombre", "").toString()
        sala = datosUsuario.getString("sala", "").toString()

        _binding!!.btnSala.setOnClickListener() {
            dialogoSala()
        }

        _binding!!.btnCerrar.setOnClickListener() {
            activity?.getSharedPreferences("datosUsuario", 0)!!.edit().putString("nombre", "")
                .apply()
            activity?.getSharedPreferences("datosUsuario", 0)!!.edit().putString("sala", "").apply()
            findNavController().navigate(R.id.nav_chat)
        }

        _binding!!.btnEnviar.setOnClickListener() {
            if (_binding?.textMensaje?.text!!.isNotBlank()) {
                /*
                Manda el mensaje por el servicio
                 */
                // ------------------- TEST -------------------
                mensaje = binding.textMensaje.text.toString()
                val fecha = ""

                ServicioChat.enviarmensaje(nombre, sala, mensaje, fecha)
                // --------------------------------------------
                cargarMensajes()
                mensajes.clear()
            } else {
                Toast.makeText(
                    this.requireContext(),
                    "Rellena el campo para poder enviar",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.textMensaje.setText("")

        }

    }
    private fun cargarMensajes() {
       val array = ServicioChat.recuperarchat()
        // ------------------- TEST -------------------
        // Recorrer el Array
        if (array != null) {
             (0 until array.length()).forEach {
                // Insertar datos en la lista
                val objeto: JSONObject = array.getJSONObject(it)
                if (objeto["sala"].toString() ==  sala){
                    mensajes.add(Mensajes(objeto["id"].toString(),objeto["nombreUsuario"].toString(),objeto["sala"].toString(),objeto["mensaje"].toString(),objeto["fecha"].toString()))
                }
             }
             if (mensajes.isNotEmpty()) {
              mensajesAdapter = MensajeAdapter(mensajes)
                 _binding!!.mensajesRecyclerView.adapter = mensajesAdapter
         }
        }


        // --------------------------------------------
    }

    private fun dialogoSala() {
        val title = getString(R.string.sala)
        val message = getString(R.string.errorSala)
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
                    activity?.getSharedPreferences("datosUsuario", 0)!!.edit().putString("sala", codigo).apply()
                // ------------------- TEST -------------------
                    ServicioChat.sala(nombre, sala)
                    mensajes.clear()
                    cargarMensajes()
                    mensajes.clear()


                // --------------------------------------------
                }
            }
            .setNegativeButton(getString(R.string.cancelar), null)
            .create()
        dialog.show()
    }

    private fun String.quitarEspacios(): String {
        return this.replace(" ", "")
    }
}