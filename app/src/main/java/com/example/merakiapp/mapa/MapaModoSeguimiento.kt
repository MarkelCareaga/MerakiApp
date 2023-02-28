package com.example.merakiapp.mapa

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.merakiapp.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapaModoSeguimiento : Service(), OnMapReadyCallback {
    // creamos un array list de juegos
    private var listaJuego = ArrayList<LatLng>()

    // definimos los lugares de los juegos
    private val juego1 = LatLng(43.421301, -2.722980)
    private val juego2 = LatLng(43.420209, -2.721071)
    private val juego3 = LatLng(43.419160, -2.722421)
    private val juego4 = LatLng(43.419639, -2.718932)
    private val juego5 = LatLng(43.42084538018336, -2.7127369295768204)
    private val juego6 = LatLng(43.424959, -2.683557)
    private val juego7 = LatLng(43.447147, -2.785151)

    override fun onMapReady(map: GoogleMap) {
    }

    // funcion para los juegos
    fun mapa(map: GoogleMap, int: Int) {
        //añadimos todos los juegos a la lista
        listaJuego.add(juego1)
        listaJuego.add(juego2)
        listaJuego.add(juego3)
        listaJuego.add(juego4)
        listaJuego.add(juego5)
        listaJuego.add(juego6)
        listaJuego.add(juego7)
        // comprobamos si el valor entero que recibimos es igual a 1
        if (int == 1) {
            // Colocar un marcador en la misma posición y decirele donde se va a encontrar Patxi y Miren
            map.addMarker(MarkerOptions().position(juego1))
            map.addMarker(
                MarkerOptions().position(juego1)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi))
            )

            // comprobamos si el valor entero que recibimos es igual a 2
        } else if (int == 2) {
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego1).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición y decirele donde se va a encontrar Patxi y Miren
            map.addMarker(MarkerOptions().position(juego2))
            map.addMarker(
                MarkerOptions().position(juego2)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi))
            )

            // comprobamos si el valor entero que recibimos es igual a 3
        } else if (int == 3) {
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego1).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego2).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición y decirele donde se va a encontrar Patxi y Miren
            map.addMarker(MarkerOptions().position(juego3))
            map.addMarker(
                MarkerOptions().position(juego3)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi))
            )


            // comprobamos si el valor entero que recibimos es igual a 4
        } else if (int == 4) {
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego1).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego2).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego3).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición y decirele donde se va a encontrar Patxi y Miren
            map.addMarker(MarkerOptions().position(juego4))
            map.addMarker(
                MarkerOptions().position(juego4)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi))
            )


            // comprobamos si el valor entero que recibimos es igual a 5
        } else if (int == 5) {
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego1).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego2).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego3).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego4).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición y decirele donde se va a encontrar Patxi y Miren
            map.addMarker(MarkerOptions().position(juego5))
            map.addMarker(
                MarkerOptions().position(juego5)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi))
            )


            // comprobamos si el valor entero que recibimos es igual a 6
        } else if (int == 6) {
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego1).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego2).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego3).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego4).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego5).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición y decirele donde se va a encontrar Patxi y Miren
            map.addMarker(MarkerOptions().position(juego6))
            map.addMarker(
                MarkerOptions().position(juego6)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi))
            )


            // comprobamos si el valor entero que recibimos es igual a 7
        } else if (int == 7) {
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego1).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego2).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego3).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego4).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego5).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego6).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición y decirele donde se va a encontrar Patxi y Miren
            map.addMarker(MarkerOptions().position(juego7))
            map.addMarker(
                MarkerOptions().position(juego7)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi))
            )

            // comprobamos si el valor entero que recibimos es igual a 8
        } else if (int == 8) {
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego1).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego2).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego3).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego4).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego5).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego6).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
            // Colocar un marcador en la misma posición verde porque esta realizado
            map.addMarker(
                MarkerOptions().position(juego7).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
            )
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}


