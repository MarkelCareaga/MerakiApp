package com.example.merakiapp.mapa

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import com.example.merakiapp.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapaModoSeguimiento: Service(), OnMapReadyCallback{
    // Puerta de San Juan
    val juego1 = LatLng(43.421301, -2.722980)
    // Badatoz Estatua
    val juego2 = LatLng(43.420209, -2.721071)
    // Feria de Pescado
    val juego3 = LatLng(43.419160, -2.722421)
    // Olatua estatua
    val juego4 = LatLng(43.419639, -2.718932)
    // Xixili
    val juego5 = LatLng(43.42084538018336, -2.7127369295768204)
    // Isla izaro
    val juego6 = LatLng(43.424959, -2.683557)
    // San juan de gaztelugatxe
    val juego7 = LatLng(43.447147, -2.785151)

    override fun onMapReady(map: GoogleMap) {

    }

    // ???
    fun map1(map: GoogleMap){
        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(juego1))
        map.addMarker(MarkerOptions().position(juego1).icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi)))
        //circle(map,juego1)
    }
    fun map2(map: GoogleMap){
        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(juego1).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego2))
        map.addMarker(MarkerOptions().position(juego2).icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi)))
    }
    fun map3(map: GoogleMap){
        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(juego1).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego2).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego3))
        map.addMarker(MarkerOptions().position(juego3).icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi)))
    }
    fun map4(map: GoogleMap){
         // Colocar un marcador en la misma posición
         map.addMarker(MarkerOptions().position(juego1).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego2).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego3).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego4))
        map.addMarker(MarkerOptions().position(juego4).icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi)))
    }
    fun map5(map: GoogleMap){
        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(juego1).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego2).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego3).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego4).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego5))
        map.addMarker(MarkerOptions().position(juego5).icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi)))
    }
    fun map6(map: GoogleMap){
        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(juego1).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego2).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego3).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego4).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego5).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego6))
        map.addMarker(MarkerOptions().position(juego6).icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi)))
    }
    fun map7(map: GoogleMap){
        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(juego1).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego2).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego3).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego4).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego5).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego6).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego7))
        map.addMarker(MarkerOptions().position(juego7).icon(BitmapDescriptorFactory.fromResource(R.drawable.mirenypatxi)))
    }
    fun mapTerminado(map: GoogleMap){
        // Colocar un marcador en la misma posición
        map.addMarker(MarkerOptions().position(juego1).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego2).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego3).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego4).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego5).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego6).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        map.addMarker(MarkerOptions().position(juego7).icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
    }
    fun circle(mMap:GoogleMap ,posicion:LatLng){
        val radio:Int = 50
        val circleOptions = CircleOptions()
            .center(posicion)
            .radius(radio.toDouble())
            .strokeColor(Color.parseColor("#0D47A1"))
            .strokeWidth(4f)
            .fillColor(Color.parseColor("#AF4046FF"))
        val circle: Circle = mMap.addCircle(circleOptions)


        //distancia_a_puntoA(localitation ,posicion,verificar)
    }
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}


