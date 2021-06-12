package com.adrorodri.sqlexamples

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseController(context: Context): SQLiteOpenHelper(context, "Usuarios", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Usuarios (${BaseColumns._ID} INTEGER PRIMARY KEY, Username TEXT, Password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertUser(username: String, password: String) {
        val columns = ContentValues()
        columns.put("Username", username)
        columns.put("Password", password)
        writableDatabase.insert("Usuarios", null, columns)
    }

    fun verificarUsuario(username: String, password: String): Usuario? {
        val cursor = readableDatabase.rawQuery("SELECT * FROM Usuarios WHERE Username = \"$username\" AND Password = \"$password\"", arrayOf())
        if(cursor.count == 0) {
            return null
        }
        cursor.moveToFirst()
        val usuario = Usuario(cursor.getString(1), cursor.getString(2))
        cursor.close()
        return usuario
    }

    fun obtenerTodosLosUsuarios(): List<Usuario> {
        val cursor = readableDatabase.rawQuery("SELECT * FROM Usuarios", arrayOf())
        val listaUsuarios = mutableListOf<Usuario>()
        while(cursor.moveToNext()) {
            val usuario = Usuario(cursor.getString(1), cursor.getString(2))
            listaUsuarios.add(usuario)
        }
        return listaUsuarios
    }

    fun eliminarUsuario(username: String) {
        writableDatabase.delete("Usuarios", "Username = \"$username\"", arrayOf())
    }

    fun cambiarPassword(username: String, password: String, nuevoPassword: String) {
        val columns = ContentValues()
        columns.put("Password", nuevoPassword)
        writableDatabase.update("Usuarios", columns, "Username = \"$username\" AND Password = \"$password\"", arrayOf())
    }
}