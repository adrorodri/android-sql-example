package com.adrorodri.sqlexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    val dbController = DatabaseController(this)
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.texto)

        dbController.insertUser("Prueba", "pass")
        dbController.insertUser("Prueba 2", "passadfsdfs")

        textView.text = dbController.obtenerTodosLosUsuarios().toString()
        textView.setOnClickListener {
            dbController.eliminarUsuario("Prueba")
            textView.text = dbController.obtenerTodosLosUsuarios().toString()
        }

        textView.setOnLongClickListener {
            dbController.cambiarPassword("Prueba 2", "passadfsdfs", "NUEVO PASS!")
            textView.text = dbController.obtenerTodosLosUsuarios().toString()
            true
        }


        Toast.makeText(
            this,
            "${dbController.verificarUsuario("Prueba", "pass")}",
            Toast.LENGTH_LONG
        ).show()
    }
}