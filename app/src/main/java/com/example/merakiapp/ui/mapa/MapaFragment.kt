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
import com.example.merakiapp.explicaciones.Explicaciones
import com.example.merakiapp.listas.ListaDialogos
import com.example.merakiapp.listas.ListaRecursos
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

class MapaFragment() : Fragment(), OnMapReadyCallback, Explicaciones {

    private lateinit var binding: FragmentMapaBinding
    private lateinit var mapa: GoogleMap

    private var listaDialogos = ListaDialogos()

    private var juego1: Boolean = false
    private var juego2: Boolean = false
    private var juego3: Boolean = false
    private var juego4: Boolean = false
    private var juego5: Boolean = false
    private var juego6: Boolean = false
    private var juego7: Boolean = false
    private var idJuego: Int = 0
    private lateinit var ubica: LatLng
    private val radio: Int = 50

    private var juegos1 = LatLng(433.421301, -2.722980)
    private var juegos2 = LatLng(433.420209, -2.721071)
    private var juegos3 = LatLng(4333.419160, -2.722421)
    private var juegos4 = LatLng(433.419639, -2.718932)
    private var juegos5 = LatLng(433.42084538018336, -2.7127369295768204)
    private var juegos6 = LatLng(433.424959, -2.683557)
    private var juegos7 = LatLng(423.447147, -2.785151)

    val locationa = Location("juego")


    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        binding.btnJugar.isEnabled = false
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
            binding.btnVerFinal.visibility = View.GONE
            // Recoger lps valores de los juegos para saber si son True o False
            juego1 = requireActivity().getSharedPreferences("juego1", 0).getBoolean("1", false)
            juego2 = requireActivity().getSharedPreferences("juego2", 0).getBoolean("2", false)
            juego3 = requireActivity().getSharedPreferences("juego3", 0).getBoolean("3", false)
            juego4 = requireActivity().getSharedPreferences("juego4", 0).getBoolean("4", false)
            juego5 = requireActivity().getSharedPreferences("juego5", 0).getBoolean("5", false)
            juego6 = requireActivity().getSharedPreferences("juego6", 0).getBoolean("6", false)
            juego7 = requireActivity().getSharedPreferences("juego7", 0).getBoolean("7", false)

            // le damos a mapa el valor de googleMap
            mapa = googleMap
            // recogemos nuestra posicion actual
            val location = LocationServices.getFusedLocationProviderClient(this.requireContext())

            activity?.let {
                // le pedimos nuestra ultima posicion
                location.lastLocation.addOnSuccessListener(
                    it
                ) { location ->
                    // comprobamos que la localizacion no sea nula
                    if (location != null) {
                        // definimos LT y LN como variables y dando el valor location Latitude a lt y Longitude a ln
                        val lt = String.valueOf(location.getLatitude())
                        val ln = String.valueOf(location.getLongitude())
                        // le damos dichos valores a ubica que es tipo LatLng
                        ubica = LatLng(lt.toFloat().toDouble(), ln.toFloat().toDouble())
                    }else{
                        // si la localizacion es nula le damos valores 0 en latitud y 0 en longitude
                        ubica = LatLng(0.toFloat().toDouble(),0.toFloat().toDouble())
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

            // la posicion de Bermeo
            val bermeo = LatLng(43.418228, -2.721624)
            // mover la camara de  mapa a bermeo y la animacion de dicha transición
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
            // cargar los valores que tendra el mapa de referencia para los juegos
            googleMap.setOnMapLoadedCallback {
                // comprobar si juego1 es true
                juego1 = activity?.getSharedPreferences("validar1", 0)?.getBoolean("validar1", false)!!
                if (juego1) {
                    // comprobar si juego2 es true
                    juego2 = activity?.getSharedPreferences("validar2", 0)?.getBoolean("validar2", false)!!
                    if (juego2) {
                        // comprobar si juego3 es true
                        juego3 = activity?.getSharedPreferences("validar3", 0)?.getBoolean("validar3", false)!!
                        if (juego3) {
                            // comprobar si juego4 es true
                            juego4 = activity?.getSharedPreferences("validar4", 0)?.getBoolean("validar4", false)!!
                            if (juego4) {
                                // comprobar si juego5 es true
                                juego5 = activity?.getSharedPreferences("validar5", 0)?.getBoolean("validar5", false)!!
                                if (juego5) {
                                    // comprobar si juego6 es true
                                    juego6 = activity?.getSharedPreferences("validar6", 0)?.getBoolean("validar6", false)!!
                                    if (juego6) {
                                        // comprobar si juego7 es true
                                        juego7 = activity?.getSharedPreferences("validar7", 0)?.getBoolean("validar7", false)!!
                                        if (juego7) {
                                            MapaModoSeguimiento().mapa(mapa, 8)

                                            binding.btnVerFinal.visibility = View.VISIBLE

                                            binding.btnVerFinal.setOnClickListener {
                                                startActivity(Intent(this.requireContext(), FinalActivity::class.java))
                                            }

                                        } else {
                                            // si juego7 es false
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
                                            MapaModoSeguimiento().mapa(mapa, idJuego)
                                        }
                                    } else {
                                        // si juego6 es false
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
                                        MapaModoSeguimiento().mapa(mapa, idJuego)
                                    }
                                } else {
                                    // si juego5 es false
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
                                    MapaModoSeguimiento().mapa(mapa, idJuego)
                                }
                            } else {
                                // si juego4 es false
                                //Puerta de San Juan
                                juegos1 = LatLng(43.421301, -2.722980)
                                //Badatoz Estatua
                                juegos2 = LatLng(43.420209, -2.721071)
                                //Feria de Pescado
                                juegos3 = LatLng(43.419160, -2.722421)
                                //Olatua estatua
                                juegos4 = LatLng(43.419639, -2.718932)

                                idJuego = 4
                                MapaModoSeguimiento().mapa(mapa, idJuego)
                            }
                        } else {
                            // si juego3 es false
                            //Puerta de San Juan
                            juegos1 = LatLng(43.421301, -2.722980)
                            //Badatoz Estatua
                            juegos2 = LatLng(43.420209, -2.721071)
                            //Feria de Pescado
                            juegos3 = LatLng(43.419160, -2.722421)

                            idJuego = 3
                            MapaModoSeguimiento().mapa(mapa, idJuego)
                        }
                    } else {
                        // si juego2 es false
                        //Puerta de San Juan
                        juegos1 = LatLng(43.421301, -2.722980)
                        //Badatoz Estatua
                        juegos2 = LatLng(43.420209, -2.721071)

                        idJuego = 2
                        MapaModoSeguimiento().mapa(mapa, idJuego)
                    }
                } else {
                    // si juego1 es false

                    //Puerta de San Juan
                    juegos1 = LatLng(43.421301, -2.722980)

                    idJuego = 1
                    MapaModoSeguimiento().mapa(mapa, idJuego)
                }
            }


        } else {


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
            binding.btnVerFinal.visibility = View.GONE

            // le damos a mapa el valor de googleMap
            mapa = googleMap
            // recogemos nuestra posicion actual

            LocationServices.getFusedLocationProviderClient(this.requireContext())
            // Habilitar la localización del usuario y varios controles en el mapa
            mapa.uiSettings.isCompassEnabled = true
            mapa.uiSettings.isZoomControlsEnabled = true
            mapa.uiSettings.isMyLocationButtonEnabled = true
            mapa.uiSettings.isRotateGesturesEnabled = true
            mapa.uiSettings.isZoomGesturesEnabled = true

            // la posicion de Bermeo
            val bermeo = LatLng(43.418228, -2.721624)
            // mover la camara de  mapa a bermeo y la animacion de dicha transición
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
            // pasar todos los marcadores en verde y se muestren
            MapaModoSeguimiento().mapa(mapa, 8)

            // pulsar el marcador donde se encuentran los juegos
            mapa.setOnMarkerClickListener{ marker ->

                // comprobar si un marker de los 7 que hay es la misma que el juego 1
                if (juego1 == marker.position) {
                    // si lo es se llama al activity de ese juego
                    val intent_puerta_san_juan = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_PuertaSanJuan)
                    startActivity(intent_puerta_san_juan)
                    //finalizamos este activity
                    activity?.finish()

                }
                // comprobar si un marker de los 7 que hay es la misma que el juego 2
                if (juego2 == marker.position) {
                    // si lo es se llama al activity de ese juego
                    val intent_badatoz = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Badatoz)
                    startActivity(intent_badatoz)
                    //finalizamos este activity
                    activity?.finish()
                }
                // comprobar si un marker de los 7 que hay es la misma que el juego 3
                if (juego3 == marker.position) {
                    // si lo es se llama al activity de ese juego
                    val intent_feria_pescado = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_FeriaPescado)
                    startActivity(intent_feria_pescado)
                    //finalizamos este activity
                    activity?.finish()
                }
                // comprobar si un marker de los 7 que hay es la misma que el juego 4
                if (juego4 == marker.position) {
                    val intent_olatua = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Olatua)
                    startActivity(intent_olatua)
                    //finalizamos este activity
                    activity?.finish()
                }
                // comprobar si un marker de los 7 que hay es la misma que el juego 5
                if (juego5 == marker.position) {
                    // si lo es se llama al activity de ese juego
                    val intent_xixili = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Xixili)
                    startActivity(intent_xixili)
                    //finalizamos este activity
                    activity?.finish()
                }
                // comprobar si un marker de los 7 que hay es la misma que el juego 6
                if (juego6 == marker.position) {
                    // si lo es se llama al activity de ese juego
                    val intent_isla_izaro = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Izaro)
                    startActivity(intent_isla_izaro)
                    //finalizamos este activity
                    activity?.finish()
                }
                // comprobar si un marker de los 7 que hay es la misma que el juego 7
                if (juego7 == marker.position) {
                    // si lo es se llama al activity de ese juego
                    val intent_gaztelugatxe = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Gaztelugatxe)
                    startActivity(intent_gaztelugatxe)
                    //finalizamos este activity
                    activity?.finish()
                }

                return@setOnMarkerClickListener true

            }

        }
        // poner el boton en funcionamiento
        binding.btnJugar.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // definir el binding con el FragmentMapaBinding y inflarlo
        binding = FragmentMapaBinding.inflate(inflater, container, false)

        // BOTONES AYUDA Y ROTACIÓN
        binding.btnAyudaMapa.setOnClickListener {
            listaDialogos.mostrar_dialog(requireActivity(), ListaRecursos.tituloMapa, ListaRecursos.mensajeMapa)
        }
        binding.btnInfoPantallaMapa.setOnClickListener {
            listaDialogos.mostrar_info_pantalla(requireActivity(), false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // comprobar si esta en modo libre
        if((activity?.getSharedPreferences("pref", 0)?.getBoolean("libre", false) == false)) {
            // si no esta en modo libre
            // hacer el boton jugar visible si es modo explorador
            binding.btnJugar.visibility = View.VISIBLE
        }else{
            // si esta en modo libre
            // hacer el boton jugar se oculte si es modo libre
            binding.btnJugar.visibility = View.GONE
        }
        //--------------------------- Boton jugar ------------------------------//
        binding.btnJugar.setOnClickListener {
            // comprobar si la ubica no es 0
            if (ubica.latitude != 0.toFloat().toDouble() && ubica.longitude != 0.toFloat().toDouble()) {
                // si el resultado true
                // comprobar si el idJuego es == 1
                if (idJuego == 1) {
                    // si es true
                    //dar valotes a locationa latitude y longitude con ubica latitude y longitude
                    locationa.latitude = ubica.latitude
                    locationa.longitude = ubica.longitude
                    // llamar a la funcion de distancia
                    distancia_a_puntoA(locationa)
                }
                // comprobar si el idJuego es == 2
                if (idJuego == 2) {
                    // si es true
                    //dar valotes a locationa latitude y longitude con ubica latitude y longitude
                    locationa.latitude = ubica.latitude
                    locationa.longitude = ubica.longitude
                    // llamar a la funcion de distancia
                    distancia_a_puntoA(locationa)
                }
                // comprobar si el idJuego es == 3
                if (idJuego == 3) {
                    // si es true
                    //dar valotes a locationa latitude y longitude con ubica latitude y longitude
                    locationa.latitude = ubica.latitude
                    locationa.longitude = ubica.longitude
                    // llamar a la funcion de distancia
                    distancia_a_puntoA(locationa)
                }
                // comprobar si el idJuego es == 4
                if (idJuego == 4) {
                    // si es true
                    //dar valotes a locationa latitude y longitude con ubica latitude y longitude
                    locationa.latitude = ubica.latitude
                    locationa.longitude = ubica.longitude
                    // llamar a la funcion de distancia
                    distancia_a_puntoA(locationa)
                }
                // comprobar si el idJuego es == 5
                if (idJuego == 5) {
                    // si es true
                    //dar valotes a locationa latitude y longitude con ubica latitude y longitude
                    locationa.latitude = ubica.latitude
                    locationa.longitude = ubica.longitude
                    // llamar a la funcion de distancia
                    distancia_a_puntoA(locationa)
                }
                // comprobar si el idJuego es == 6
                if (idJuego == 6) {
                    // si es true
                    //dar valotes a locationa latitude y longitude con ubica latitude y longitude
                    locationa.latitude = ubica.latitude
                    locationa.longitude = ubica.longitude
                    // llamar a la funcion de distancia
                    distancia_a_puntoA(locationa)
                }
                // comprobar si el idJuego es == 7
                if (idJuego == 7) {
                    // si es true
                    //dar valotes a locationa latitude y longitude con ubica latitude y longitude
                    locationa.latitude = ubica.latitude
                    locationa.longitude = ubica.longitude
                    // llamar a la funcion de distancia
                    distancia_a_puntoA(locationa)
                }
            } else {
                // si el resultado true
                // es porque no tiene el gps activaso
                Toast.makeText(this.requireActivity(), R.string.errorUbicacion, Toast.LENGTH_SHORT).show()
            }
        }
        // dar al mapFragment el valor del callback
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        }



// funcion de distancia a los juegos
    private fun distancia_a_puntoA(localitation: Location) {
        //definimos la localizacion
        val location1 = Location("Juego")
        location1.latitude = juegos1.latitude
        location1.longitude = juegos1.longitude
        //comprobamos si estamos cerca del juego1
        if (localitation.distanceTo(location1) <= radio.toDouble()) {
            //si estamos a 50 metros o menos
            //finalizamos este activity
            activity?.finish()

            // llamamos al activity del juegoq1 y lo lanzamos
            val intent_puerta_san_juan = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_PuertaSanJuan)
            startActivity(intent_puerta_san_juan)

        } else {
            //si no estamos a 50 metros o menos
            //definimos la localizacion
            val location2 = Location("Juego")
            location2.latitude = juegos2.latitude
            location2.longitude = juegos2.longitude
            //comprobamos si estamos cerca del juego2
            if (localitation.distanceTo(location2) <= radio.toDouble()) {
                //si estamos a 50 metros o menos
                //finalizamos este activity
                activity?.finish()

                // llamamos al activity del juegoq2 y lo lanzamos
                val intent_badatoz = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Badatoz)
                startActivity(intent_badatoz)
            } else {
                //si no estamos a 50 metros o menos
                //definimos la localizacion
                val location3 = Location("Juego")
                location3.latitude = juegos3.latitude
                location3.longitude = juegos3.longitude

                //comprobamos si estamos cerca del juego3
                if (localitation.distanceTo(location3) <= radio.toDouble()) {
                    //si estamos a 50 metros o menos
                    //finalizamos este activity
                    activity?.finish()

                    // llamamos al activity del juegoq3 y lo lanzamos
                    val intent_feria_pescado = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_FeriaPescado)
                    startActivity(intent_feria_pescado)
                } else {
                    //si no estamos a 50 metros o menos
                    //definimos la localizacion
                    val location4 = Location("Juego")
                    location4.latitude = juegos4.latitude
                    location4.longitude = juegos4.longitude

                    //comprobamos si estamos cerca del juego4
                    if (localitation.distanceTo(location4) <= radio.toDouble()) {
                        //si estamos a 50 metros o menos
                        //finalizamos este activity
                        activity?.finish()

                        // llamamos al activity del juegoq4 y lo lanzamos
                        val intent_olatua = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Olatua)
                        startActivity(intent_olatua)
                    } else {
                        //si no estamos a 50 metros o menos
                        //definimos la localizacion
                        val location5 = Location("Juego")
                        location5.latitude = juegos5.latitude
                        location5.longitude = juegos5.longitude

                        //comprobamos si estamos cerca del juego5
                        if (localitation.distanceTo(location5) <= radio.toDouble()) {
                            //si estamos a 50 metros o menos
                            //finalizamos este activity
                            activity?.finish()

                            // llamamos al activity del juegoq5 y lo lanzamos
                            val intent_xixili = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Xixili)
                            startActivity(intent_xixili)

                        } else {
                            //si no estamos a 50 metros o menos
                            //definimos la localizacion
                            val location6 = Location("Juego")
                            location6.latitude = juegos6.latitude
                            location6.longitude = juegos6.longitude

                            //comprobamos si estamos cerca del juego6
                            if (localitation.distanceTo(location6) <= radio.toDouble()) {
                                //si estamos a 50 metros o menos
                                //finalizamos este activity
                                activity?.finish()

                                // llamamos al activity del juegoq6 y lo lanzamos
                                val intent_isla_izaro = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Izaro)
                                startActivity(intent_isla_izaro)
                            } else {
                                //si no estamos a 50 metros o menos
                                //definimos la localizacion
                                val location7 = Location("Juego")
                                location7.latitude = juegos7.latitude
                                location7.longitude = juegos7.longitude

                                //comprobamos si estamos cerca del juego7
                                if (localitation.distanceTo(location7) <= radio.toDouble()) {
                                    //si estamos a 50 metros o menos
                                    //finalizamos este activity
                                    activity?.finish()

                                    // llamamos al activity del juegoq7 y lo lanzamos
                                    val intent_gaztelugatxe = abrirExplicacion(this.requireActivity(), ListaRecursos.pantalla_Gaztelugatxe)
                                    startActivity(intent_gaztelugatxe)
                                } else {
                                    //si no estamos a 50 metros o menos de ningun juego
                                    Toast.makeText(this.requireContext(),getString(R.string.distancia), Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {

    }

}


