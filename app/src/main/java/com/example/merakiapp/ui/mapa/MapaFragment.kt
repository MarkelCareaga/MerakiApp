package com.example.merakiapp.ui.mapa
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.merakiapp.*
import com.example.merakiapp.databinding.FragmentMapaBinding
import com.example.merakiapp.juegos.*
import com.example.merakiapp.mapa.MapaModoSeguimiento
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.String
import kotlin.Boolean
import kotlin.Int
import kotlin.arrayOf
import kotlin.let


class MapaFragment() : Fragment(), OnMapReadyCallback, Dialogos, Explicaciones {

    private lateinit var binding: FragmentMapaBinding
    private lateinit var mapa:GoogleMap

    private var pantallaSeleccionada = ""
    private var audioSeleccionado = 0
    private var fondoSeleccionado = 0

    var juego1 : Boolean = false
    var juego2 : Boolean = false
    var juego3 : Boolean = false
    var juego4 : Boolean = false
    var juego5 : Boolean = false
    var juego6 : Boolean = false
    var juego7 : Boolean = false
    var idJuego :Int = 0
    var juego:Boolean= false
    lateinit var ubica: LatLng
    val radio:Int = 50

    //Puerta de San Juan
    var juegos1 = LatLng(433.421301, -2.722980)
    //Badatoz Estatua
    var juegos2 = LatLng(433.420209, -2.721071)
    //Feria de Pescado
    var juegos3 = LatLng(4333.419160, -2.722421)
    //Olatua estatua
    var juegos4 = LatLng(433.419639, -2.718932)
    //Xixili
    var juegos5 = LatLng(433.42084538018336, -2.7127369295768204)
    //isla izaro
    var juegos6 = LatLng(433.424959, -2.683557)
    // san juan de gaztelugatxe
    var juegos7 = LatLng(423.447147, -2.785151)
    val locationa = Location("juego")

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->

        if((activity?.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false)) {
            /**
             * Manipulates the map once available.
             * This callback is triggered when the map is ready to be used.
             * This is where we can add markers or lines, add listeners or move the camera.
             * In this case, we just add a marker near Sydney, Australia.
             * If Google Play services is not installed on the device, the user will be prompted to
             * install it inside the SupportMapFragment. This method will only be triggered once the
             * user has installed Google Play services and returned to the app.
             */

            /**
             *
             *
             *
             * MODO GUIADO
             *
             *
             *
             *
             */





            // Verifica si la preferencia "libre" es igual a false
            juego1 = requireActivity().getSharedPreferences("juego1", 0).getBoolean("1", false)
            juego2 = requireActivity().getSharedPreferences("juego2", 0).getBoolean("2", false)
            juego3 = requireActivity().getSharedPreferences("juego3", 0).getBoolean("3", false)
            juego4 = requireActivity().getSharedPreferences("juego4", 0).getBoolean("4", false)
            juego5 = requireActivity().getSharedPreferences("juego5", 0).getBoolean("5", false)
            juego6 = requireActivity().getSharedPreferences("juego6", 0).getBoolean("6", false)
            juego7 = requireActivity().getSharedPreferences("juego7", 0).getBoolean("7", false)

            mapa = googleMap

            val location = LocationServices.getFusedLocationProviderClient(this.requireContext())
            activity?.let {
                location.lastLocation.addOnSuccessListener(
                    it
                ) { location ->
                    if (location != null) {
                        val lt = String.valueOf(location.getLatitude())
                        val ln = String.valueOf(location.getLongitude())
                        ubica = LatLng(lt.toFloat().toDouble(), ln.toFloat().toDouble())
                    }
                }
            }


            // Habilitar la localización del usuario y varios controles en el mapa
            mapa.isMyLocationEnabled = true
            mapa.uiSettings.isCompassEnabled = true
            mapa.uiSettings.isZoomControlsEnabled = true
            mapa.uiSettings.isMyLocationButtonEnabled = true
            mapa.uiSettings.isRotateGesturesEnabled = true
            mapa.uiSettings.isZoomGesturesEnabled = true

            val bermeo = LatLng(43.418228, -2.721624)

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(bermeo))
            mapa.moveCamera(CameraUpdateFactory.newLatLng(bermeo))
            mapa.animateCamera(CameraUpdateFactory.newLatLng(bermeo))
            val camera = CameraPosition.builder()
                .target(bermeo)
                .zoom(15f)
                .bearing(0f)
                .tilt(0f)
                .build()
            mapa.animateCamera(CameraUpdateFactory.newCameraPosition(camera))

            googleMap.setOnMapLoadedCallback {
                juego1 = activity?.getSharedPreferences("validar1", 0)?.getBoolean("validar1", false)!!
                if (juego1) {
                    juego2 = activity?.getSharedPreferences("validar2", 0)?.getBoolean("validar2", false)!!
                    if (juego2) {
                        juego3 = activity?.getSharedPreferences("validar3", 0)?.getBoolean("validar3", false)!!
                        if (juego3) {
                            juego4 = activity?.getSharedPreferences("validar4", 0)?.getBoolean("validar4", false)!!
                            if (juego4) {
                                juego5 = activity?.getSharedPreferences("validar5", 0)?.getBoolean("validar5", false)!!

                                if (juego5) {
                                    juego6 = activity?.getSharedPreferences("validar6", 0)?.getBoolean("validar6", false)!!

                                    if (juego6) {
                                        juego7 = activity?.getSharedPreferences("validar7", 0)?.getBoolean("validar7", false)!!

                                        if (juego7) {
                                            MapaModoSeguimiento().mapTerminado(mapa)

                                        } else {
                                            //Puerta de San Juan
                                            juegos1 = LatLng(43.421301, -2.722980)
                                            //Badatoz Estatua
                                            juegos2 = LatLng(43.420209, -2.721071)
                                            //Feria de Pescado
                                            juegos3 = LatLng(43.419160, -2.722421)
                                            //Olatua estatua
                                            juegos4 = LatLng(43.419639, -2.718932)
                                            //Xixili
                                            juegos5 = LatLng(43.42084538018336, -2.7127369295768204)
                                            //isla izaro
                                            juegos6 = LatLng(43.424959, -2.683557)
                                            // san juan de gaztelugatxe
                                            juegos7 = LatLng(43.447147, -2.785151)
                                            idJuego = 7
                                            MapaModoSeguimiento().map7(mapa)
                                        }
                                    } else {
                                        //Puerta de San Juan
                                        juegos1 = LatLng(43.421301, -2.722980)
                                        //Badatoz Estatua
                                        juegos2 = LatLng(43.420209, -2.721071)
                                        //Feria de Pescado
                                        juegos3 = LatLng(43.419160, -2.722421)
                                        //Olatua estatua
                                        juegos4 = LatLng(43.419639, -2.718932)
                                        //Xixili
                                        juegos5 = LatLng(43.42084538018336, -2.7127369295768204)
                                        //isla izaro
                                        juegos6 = LatLng(43.424959, -2.683557)

                                        idJuego = 6
                                        MapaModoSeguimiento().map6(mapa)
                                    }
                                } else {
                                    //Puerta de San Juan
                                    juegos1 = LatLng(43.421301, -2.722980)
                                    //Badatoz Estatua
                                    juegos2 = LatLng(43.420209, -2.721071)
                                    //Feria de Pescado
                                    juegos3 = LatLng(43.419160, -2.722421)
                                    //Olatua estatua
                                    juegos4 = LatLng(43.419639, -2.718932)
                                    //Xixili
                                    juegos5 = LatLng(43.42084538018336, -2.7127369295768204)

                                    idJuego = 5
                                    MapaModoSeguimiento().map5(mapa)
                                }
                            } else {
                                //Puerta de San Juan
                                juegos1 = LatLng(43.421301, -2.722980)
                                //Badatoz Estatua
                                juegos2 = LatLng(43.420209, -2.721071)
                                //Feria de Pescado
                                juegos3 = LatLng(43.419160, -2.722421)
                                //Olatua estatua
                                juegos4 = LatLng(43.419639, -2.718932)

                                idJuego = 4
                                MapaModoSeguimiento().map4(mapa)
                            }
                        } else {
                            //Puerta de San Juan
                            juegos1 = LatLng(43.421301, -2.722980)
                            //Badatoz Estatua
                            juegos2 = LatLng(43.420209, -2.721071)
                            //Feria de Pescado
                            juegos3 = LatLng(43.419160, -2.722421)

                            idJuego = 3
                            MapaModoSeguimiento().map3(mapa)
                        }
                    } else {
                        //Puerta de San Juan
                        juegos1 = LatLng(43.421301, -2.722980)
                        //Badatoz Estatua
                        juegos2 = LatLng(43.420209, -2.721071)

                        idJuego = 2
                        MapaModoSeguimiento().map2(mapa)
                    }
                } else {
                    //Puerta de San Juan
                    juegos1 = LatLng(43.421301, -2.722980)

                    idJuego = 1
                    MapaModoSeguimiento().map1(mapa)
                }
            }


        }else {


            /**
             *
             *
             *
             * MODO LIBRE
             *
             *
             *
             *
             */
            mapa = googleMap

            val location = LocationServices.getFusedLocationProviderClient(this.requireContext())

            mapa.uiSettings.isCompassEnabled = true
            mapa.uiSettings.isZoomControlsEnabled = true
            mapa.uiSettings.isMyLocationButtonEnabled = true
            mapa.uiSettings.isRotateGesturesEnabled = true
            mapa.uiSettings.isZoomGesturesEnabled = true

            val bermeo = LatLng(43.418228, -2.721624)

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(bermeo))
            mapa.moveCamera(CameraUpdateFactory.newLatLng(bermeo))
            mapa.animateCamera(CameraUpdateFactory.newLatLng(bermeo))
            val camera = CameraPosition.builder()
                .target(bermeo)
                .zoom(15f)
                .bearing(0f)
                .tilt(0f)
                .build()
            mapa.animateCamera(CameraUpdateFactory.newCameraPosition(camera))

            //Puerta de San Juan
            val juego1 = LatLng(43.421301, -2.722980)
            //Badatoz Estatua
            val juego2 = LatLng(43.420209, -2.721071)
            //Feria de Pescado
            val juego3 = LatLng(43.419160, -2.722421)
            //Olatua estatua
            val juego4 = LatLng(43.419639, -2.718932)
            //Xixili
            val juego5 = LatLng(43.42084538018336, -2.7127369295768204)
            //isla izaro
            val juego6 = LatLng(43.424959, -2.683557)
            // san juan de gaztelugatxe
            val juego7 = LatLng(43.447147, -2.785151)
            // Colocar un marcador en la misma posición
            mapa.addMarker(MarkerOptions().position(juego1).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            mapa.addMarker(MarkerOptions().position(juego2).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            mapa.addMarker(MarkerOptions().position(juego3).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            mapa.addMarker(MarkerOptions().position(juego4).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            mapa.addMarker(MarkerOptions().position(juego5).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            mapa.addMarker(MarkerOptions().position(juego6).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            mapa.addMarker(MarkerOptions().position(juego7).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))

            mapa.setOnMarkerClickListener{ marker ->
                val juego = marker.position
                if(juego1 == marker.position){
                    pantallaSeleccionada = "puerta_de_san_juan"
                    audioSeleccionado = R.raw.audiopuertadesanjuan
                    fondoSeleccionado = R.drawable.fondopuertasanjuan

                    var intent_puerta_san_juan = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_puerta_san_juan)
                }
                if(juego2 == marker.position){
                    pantallaSeleccionada = "badatoz_estatua"
                    audioSeleccionado = R.raw.audiobadatoz
                    fondoSeleccionado = R.drawable.fondobadatoz

                    var intent_badatoz = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_badatoz)
                }
                if(juego3 == marker.position){
                    pantallaSeleccionada = "feria_del_pescado"
                    audioSeleccionado = R.raw.audioferiadelpescado
                    fondoSeleccionado = R.drawable.fondoferiapescado

                    var intent_feria_pescado = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_feria_pescado)
                }
                if(juego4 == marker.position){
                    pantallaSeleccionada = "olatua_estatua"
                    audioSeleccionado = R.raw.audioolatua
                    fondoSeleccionado = R.drawable.fondoolatua

                    var intent_olatua = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_olatua)
                }
                if(juego5 == marker.position){
                    pantallaSeleccionada = "xixili"
                    audioSeleccionado = R.raw.audioxixili
                    fondoSeleccionado = R.drawable.fondoxixili

                    var intent_xixili = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_xixili)
                }
                if(juego6 == marker.position){
                    pantallaSeleccionada = "isla_de_izaro"
                    audioSeleccionado = R.raw.audioisladeizaro
                    fondoSeleccionado = R.drawable.fondoizaro1

                    var intent_isla_izaro = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_isla_izaro)
                }
                if(juego7 == marker.position){
                    pantallaSeleccionada = "gaztelugatxe"
                    audioSeleccionado = R.raw.audiosanjuandegaztelugatxe
                    fondoSeleccionado = R.drawable.fondogaztelugatxe

                    var intent_gaztelugatxe = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_gaztelugatxe)
                }
                return@setOnMarkerClickListener true

            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapaBinding.inflate(inflater, container, false)

        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaMapa.setOnClickListener {
            val mensaje = "Modo Explorador: Para acceder a un juego, acercate lo máximo posible a dicha ubicación y pulsa el botón JUGAR. \n \n"+
                    "Modo Libre: Pulsa únicamente las ubicaciones para acceder a sus respectivos juegos."
            mostrar_dialog(requireActivity(), "MAPA INTERACTIVO", mensaje)
        }
        binding.btnInfoPantallaMapa.setOnClickListener {
            mostrar_info_pantalla(requireActivity(), false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if((activity?.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false)) {
            binding.btnJugar.visibility = View.VISIBLE
        }else{
            binding.btnJugar.visibility = View.GONE
        }

        binding.btnJugar.setOnClickListener {
            if (idJuego == 1) {

                locationa.latitude = ubica?.latitude!!
                locationa.longitude = ubica?.longitude!!
                distancia_a_puntoA(locationa)

            }
            if (idJuego == 2) {

                locationa.latitude = ubica?.latitude!!
                locationa.longitude = ubica?.longitude!!
                distancia_a_puntoA(locationa)
            }
            if (idJuego == 3) {

                locationa.latitude = ubica?.latitude!!
                locationa.longitude = ubica?.longitude!!
                distancia_a_puntoA(locationa)
            }
            if (idJuego == 4) {

                locationa.latitude = ubica?.latitude!!
                locationa.longitude = ubica?.longitude!!
                distancia_a_puntoA(locationa)
            }
            if (idJuego == 5) {

                locationa.latitude = ubica?.latitude!!
                locationa.longitude = ubica?.longitude!!
                distancia_a_puntoA(locationa)
            }
            if (idJuego == 6) {

                locationa.latitude = ubica?.latitude!!
                locationa.longitude = ubica?.longitude!!
                distancia_a_puntoA(locationa)
            }
            if (idJuego == 7) {

                locationa.latitude = ubica?.latitude!!
                locationa.longitude = ubica?.longitude!!
                distancia_a_puntoA(locationa)
            }
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        }


    override fun onMapReady(googlemap: GoogleMap) {
        mapa = googlemap

        // pedirPermisos()
        if (MapaFragment().context?.let {
                ActivityCompat.checkSelfPermission(
                    it,android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
            != PackageManager.PERMISSION_GRANTED
            && MapaFragment().context?.let {
                ActivityCompat.checkSelfPermission(
                    it, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            !=PackageManager.PERMISSION_GRANTED){
            MapaFragment().activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION),
                    1)
            }
            return
        }

        mapa.isMyLocationEnabled=true
    }
    private fun distancia_a_puntoA(localitation: Location) {
        val location1 = Location("Juego")
        location1.latitude = juegos1.latitude
        location1.longitude = juegos1.longitude

        if (localitation.distanceTo(location1) <= radio.toDouble()) {
            activity?.finish()

            pantallaSeleccionada = "puerta_de_san_juan"
            audioSeleccionado = R.raw.audiopuertadesanjuan
            fondoSeleccionado = R.drawable.fondopuertasanjuan

            var intent_puerta_san_juan = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
            startActivity(intent_puerta_san_juan)

        }else{
            val location2 = Location("Juego")
            location2.latitude = juegos2.latitude
            location2.longitude = juegos2.longitude

            if (localitation.distanceTo(location2) <= radio.toDouble()) {
                activity?.finish()

                pantallaSeleccionada = "badatoz_estatua"
                audioSeleccionado = R.raw.audiobadatoz
                fondoSeleccionado = R.drawable.fondobadatoz

                var intent_badatoz = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                startActivity(intent_badatoz)

            }else {
                val location3 = Location("Juego")
                location3.latitude = juegos3.latitude
                location3.longitude = juegos3.longitude
                if (localitation.distanceTo(location3) <= radio.toDouble()) {
                    activity?.finish()

                    pantallaSeleccionada = "feria_del_pescado"
                    audioSeleccionado = R.raw.audioferiadelpescado
                    fondoSeleccionado = R.drawable.fondoferiapescado

                    var intent_feria_pescado = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                    startActivity(intent_feria_pescado)

                } else {
                    val location4 = Location("Juego")
                    location4.latitude = juegos4.latitude
                    location4.longitude = juegos4.longitude
                    if (localitation.distanceTo(location4) <= radio.toDouble()) {
                        activity?.finish()

                        pantallaSeleccionada = "olatua_estatua"
                        audioSeleccionado = R.raw.audioolatua
                        fondoSeleccionado = R.drawable.fondoolatua

                        var intent_olatua = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                        startActivity(intent_olatua)

                    } else {
                        val location5 = Location("Juego")
                        location5.latitude = juegos5.latitude
                        location5.longitude = juegos5.longitude

                        if (localitation.distanceTo(location5) <= radio.toDouble()) {
                            activity?.finish()

                            pantallaSeleccionada = "xixili"
                            audioSeleccionado = R.raw.audioxixili
                            fondoSeleccionado = R.drawable.fondoxixili

                            var intent_xixili = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                            startActivity(intent_xixili)

                        } else {
                            val location6 = Location("Juego")
                            location6.latitude = juegos6.latitude
                            location6.longitude = juegos6.longitude

                            if (localitation.distanceTo(location6) <= radio.toDouble()) {
                                activity?.finish()

                                pantallaSeleccionada = "isla_de_izaro"
                                audioSeleccionado = R.raw.audioisladeizaro
                                fondoSeleccionado = R.drawable.fondoizaro1

                                var intent_isla_izaro = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                                startActivity(intent_isla_izaro)

                            } else {
                                val location7 = Location("Juego")
                                location7.latitude = juegos7.latitude
                                location7.longitude = juegos7.longitude

                                if (localitation.distanceTo(location7) <= radio.toDouble()) {
                                    activity?.finish()

                                    pantallaSeleccionada = "gaztelugatxe"
                                    audioSeleccionado = R.raw.audiosanjuandegaztelugatxe
                                    fondoSeleccionado = R.drawable.fondogaztelugatxe

                                    var intent_gaztelugatxe = abrirExplicacion(this.requireActivity(), pantallaSeleccionada, audioSeleccionado, fondoSeleccionado)
                                    startActivity(intent_gaztelugatxe)

                                }else{
                                    Toast.makeText(this.requireContext(),getString(R.string.distancia), Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


