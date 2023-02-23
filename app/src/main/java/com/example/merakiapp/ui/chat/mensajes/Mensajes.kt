package com.example.merakiapp.ui.chat.mensajes

import java.util.Date

data class Mensajes(
    var id: String,
    var nombreUusuario: String,
    var sala:String,
    var mensaje: String,
    var fecha:Date?
    )