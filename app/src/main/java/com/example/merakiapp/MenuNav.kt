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


        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_map, R.id.nav_acerca, R.id.nav_inicio, R.id.nav_help, R.id.nav_salida
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        val salida = navView.menu.findItem(R.id.nav_salida)

        salida.setOnMenuItemClickListener(){
            borrar = true
            finish()
            return@setOnMenuItemClickListener true
        }

        val inicio = navView.menu.findItem(R.id.nav_inicio)

        inicio.setOnMenuItemClickListener(){
            borrar = false
            finish()
            return@setOnMenuItemClickListener true
        }

        navView.setupWithNavController(navController)
    }




    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }




    override fun onDestroy() {
        super.onDestroy()
        if (borrar) {
            System.exit(0)
            Process.killProcess(Process.myPid())
        }
    }

    override fun onBackPressed() {

            borrar = false

            finish()
            return
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
    //mapa

}









