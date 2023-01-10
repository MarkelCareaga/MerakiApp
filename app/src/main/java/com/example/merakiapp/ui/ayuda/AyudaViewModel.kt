package com.example.merakiapp.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AyudaViewModel : ViewModel() {
    // Declara una variable mutable _text de tipo MutableLiveData<String>
    private val _text = MutableLiveData<String>().apply {
        // Establece un valor inicial vacío para la variable _text
        value = ""
    }
    // Expone una variable de solo lectura text como una variable LiveData<String>
    val text: LiveData<String> = _text
}