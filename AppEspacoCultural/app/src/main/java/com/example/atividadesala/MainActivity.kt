package com.example.atividadesala

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnVisit = findViewById<Button>(R.id.button)
        var btnAdm = findViewById<Button>(R.id.button2)

        btnVisit.setOnClickListener(){
                startActivity(Intent(this, MainActivityVisit::class.java))
        }

        btnAdm.setOnClickListener(){
            startActivity(Intent(this, MainActivityAdmLogin::class.java))
        }

    }
}
