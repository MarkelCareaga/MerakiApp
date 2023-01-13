package com.example.merakiapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface UsuarioDAO {
    @Query("SELECT * FROM Usuarios")
    fun TodosUsuarios(): List<Usuario>
    @Query("SELECT * FROM Usuarios where id =:id")
    fun buscarUsuarioenLista(id:Long): Usuario
    @Insert(onConflict = REPLACE)
    fun insertarUsuario(usuario: Usuario)
    @Delete
    fun borrarUsuario(usuario: Usuario)
    @Update
    fun modificarUsuario(usuario: Usuario)




}