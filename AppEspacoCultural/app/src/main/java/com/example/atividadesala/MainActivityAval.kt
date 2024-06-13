package com.example.atividadesala

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivityAval : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_aval)

        val btnSend = findViewById<Button>(R.id.submit_button)
        btnSend.setOnClickListener(){
            Toast.makeText(this, "Feedback enviado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivityVisit::class.java))
        }

        val btnBack = findViewById<Button>(R.id.buttonBackVisit)
        btnBack.setOnClickListener(){
            startActivity(Intent(this, MainActivityVisit::class.java))
        }
    }
}