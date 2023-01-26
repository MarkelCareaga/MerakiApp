package com.example.merakiapp.sqLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class UsuarioDB(context: Context): SQLiteOpenHelper(context,"UsuarioDB.db", null, 1) {
    companion object {
        // nombre de la tabla
        const val NOMBRE_TABLA = "usuarios"
        // campos de la tabla
        //id de la tabla
        const val CAMPO_ID = "id"
        //nombre Usuario  de la tabla
        const val CAMPO_NAME_USUARIO = "nombreUsuario"
        //Pasos usuarios de la tabla
        const val CAMPO_PASOS_USUARIOS = "pasosUsuarios"
        //Pasos usuarios de la tabla
        const val CAMPO_IMAGEN = "imageUsuario"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        // creamos la tabla con las referencias del companion object
        val crear = "CREATE TABLE $NOMBRE_TABLA ($CAMPO_ID INTEGER PRIMARY KEY, $CAMPO_NAME_USUARIO TEXT, $CAMPO_PASOS_USUARIOS INTEGER, $CAMPO_IMAGEN TEXT)"
        // creamos la tabla en el room y nunca va a ser nulla
        db!!.execSQL(crear)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // creamo la referencia para borrar la tabla
        val borrar = "drop table $NOMBRE_TABLA"
        // borramos la tabla del room y nunca va a ser nulla
        db!!.execSQL(borrar)
        //llamamos al oncreate para volver a crear la tablas
        onCreate(db)
    }
    fun insertar_datos(id:Int, nombreUsuario:String, pasosUsuario:Int, imagen: String?){
        //decimos que la tabla es modificable y de lectura
        val db = this.writableDatabase
        /*
        FORMA 1 DE HACERLO

        val datos = ContentValues()
        //añadimos a datos los valores que queremos pasar a la tabla



         */
        //FORMA 2 DE HACERLO
        //datos coge el valor del contenido
        val datos = ContentValues().apply{
            //añadimos a datos los valores que queremos pasar a la tabla
            put(CAMPO_ID, id)
            put(CAMPO_NAME_USUARIO, nombreUsuario)
            put(CAMPO_PASOS_USUARIOS, pasosUsuario)
            put(CAMPO_IMAGEN, imagen)

        }

        //insertamos los datos recogidos a la tabla
        db.insert(NOMBRE_TABLA, null, datos)
    }
    fun listaTodos(): MutableList<Usuario>{
        // creamos una lista tipo Alumnos
        val listaAlumnos: MutableList<Usuario> = arrayListOf()
        //decimos que la tabla sea de lectura
        val db = this.readableDatabase
        //recogemos en el select en una variable
        val cursor = db.rawQuery("SELECT * FROM $NOMBRE_TABLA", null)
        // nos movemos al primero de la tabla
        if (cursor.moveToFirst()){
            //si hay valores en la tabla
            do{
                // añadimos a una variable los datos que hemos obtenido del cursor en
                val todo = Usuario(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3))
                // añadimos a la lista los valores de
                listaAlumnos.add(todo)
            }
            // se ejecutara mientras haya datos en las siguientes filas
            while(cursor.moveToNext())
        }
        //se devuelve listaAlumnos
        return listaAlumnos

    }
    fun borrarDatos(id: Long): Int {
        //decimos que la tabla es modificable y de lectura
        val db = this.writableDatabase
        // devuelve  numeros de borrados
        return db.delete(NOMBRE_TABLA, "$CAMPO_ID=?", arrayOf(id.toString()))
        //db.close()
    }



    fun actualizarDatos(id:Int, nombreUsuario:String, pasosUsuario:Int, imagen: String?){
        //decimos que la tabla es modificable y de lectura
        val db = this.writableDatabase
        val datos = ContentValues().apply {
            put(CAMPO_ID, id)
            put(CAMPO_NAME_USUARIO, nombreUsuario)
            put(CAMPO_PASOS_USUARIOS, pasosUsuario)
            put(CAMPO_IMAGEN, imagen)
        }
        db.update("NOMBRE_TABLA",datos, "$CAMPO_ID=?", arrayOf(id.toString()))
    }
}