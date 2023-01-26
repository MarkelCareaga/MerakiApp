package com.example.merakiapp.ui.acercade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AcercadeViewModel : ViewModel() {

    // Texto referente a la empresa que solicita la aplicación.
    private val _texto_upv = MutableLiveData<String>().apply {
        value = "Kaixo! Somos un grupo de 3° de educación primaria trilingüe de la " +
                "UPV/EHU y hemos diseñado esta unidad didáctica sobre una excursión a " +
                "Bermeo. \n Para nosotros es muy importante sacar al alumnado del aula para " +
                "que aprendan de una forma más lúdica y divertida."
    }

    // Texto referente al equipo de desarrollo de la aplicación.
    private val _texto_grupo3 = MutableLiveData<String>().apply {
        value = "Aplicación desarrollada por el equipo GRUPO 3 de la empresa MIME, perteneciente " +
                "al centro CIFP TXURDINAGA LHII. \n" +
                "Integrantes del GRUPO 3: \n - Iker Martín \n - Mateo Vargas \n - Endika Villafruela \n - Markel Kareaga"
    }

    // Texto referente al equipo de desarrollo de la aplicación.
    private val _texto_copyright = MutableLiveData<String>().apply {
        value = "Copyright @ 2022 - 2023 Meraki App. - All Rights Reserved."
    }

    val texto_upv: LiveData<String> = _texto_upv
    val texto_grupo3: LiveData<String> = _texto_grupo3
    val texto_copyright: LiveData<String> = _texto_copyright
}