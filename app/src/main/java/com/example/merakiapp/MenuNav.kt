package com.example.merakiapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.merakiapp.databinding.ActivityMainBinding
import com.example.merakiapp.explicaciones.ExplicacionesActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.navigation.NavigationView


class MenuNav : AppCompatActivity(), OnMapReadyCallback {


    var borrar = false

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val context:Context = this

    @SuppressLint("ResourceType", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        // Deshabilitar rotación de pantalla (Landscape)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        super.onCreate(savedInstanceState)
        // le pasamos a libre el dato guardado de inicio a menuNav
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


       /*
        acceder a assets


            val audio  = assets.open("ahoraostoca.m4a")


        */

        // se establece una barra de herramientas
        setSupportActionBar(binding.appBarMain.toolbar)

        // se crea un DrawerLayout y un NavigationView
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        // se obtiene un controlador de navegación
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        // se establecen las opciones de navegación
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_map, R.id.nav_acerca, R.id.nav_inicio, R.id.nav_help, R.id.nav_salida
            ), drawerLayout
        )
        // se configura la barra de herramientas con el controlador de navegación
        setupActionBarWithNavController(navController, appBarConfiguration)
        // se obtiene el elemento "salida" del menú de navegación
        val salida = navView.menu.findItem(R.id.nav_salida)

        // se establece un escucha de clic en el elemento "salida" del menú de navegación
        salida.setOnMenuItemClickListener(){
            borrar = true
            finish()
            return@setOnMenuItemClickListener true
        }

        // se obtiene el elemento "inicio" del menú de navegación
        val inicio = navView.menu.findItem(R.id.nav_inicio)

        // se establece un escucha de clic en el elemento "inicio" del menú de navegación
        inicio.setOnMenuItemClickListener(){
            borrar = false
            finish()
            return@setOnMenuItemClickListener true
        }

        // se configura el NavigationView con el controlador de navegación
        navView.setupWithNavController(navController)
    }



    // sobreescribe el metodo onSupportNavigateUp para navegar con el controlador de navegación
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    // sobreescribe el metodo onDestroy para cerrar la aplicacion completamente si se ha establecido borrar como true
    override fun onDestroy() {
        super.onDestroy()
        if (borrar) {
            System.exit(0)
            Process.killProcess(Process.myPid())
        }
    }
    // sobreescribe el metodo onBackPressed para salir de la aplicación sin cerrarla completamente
    override fun onBackPressed() {

            borrar = false

            finish()
            return
    }
    // implementa el metodo onMapReady pero no hace nada ya que el TODO("Not yet implemented") indica que todavia no esta implementado
    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
    //mapa

}









