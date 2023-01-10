package com.example.merakiapp.room

import androidx.room.*

@Dao
interface UsuarioDAO {
    @Query("SELECT * FROM Usuario")
    fun buscarUsuarios(): List<Usuario>
    @Query("SELECT * FROM Usuario where id in (:lista)")
    fun buscarUsuarioenLista(lista:List<String>): List<Usuario>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarUsuario(usuario: Usuario)
    @Delete
    fun borrarUsuario(usuario: Usuario)
    @Update
    fun modificarUsuario(usuario: Usuario)




}