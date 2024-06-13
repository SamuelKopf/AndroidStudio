package com.example.atividadesala

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivityAdmLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_adm_login)

        val btnEntry = findViewById<Button>(R.id.buttonEntry)
        val btnBack = findViewById<Button>(R.id.buttonBackMain3)
        val etLogin = findViewById<EditText>(R.id.editTextLogin)
        val etSenha = findViewById<EditText>(R.id.editTextSenha)

        val login = "adm"
        val senha = "123"

        println(etLogin.toString())
        println(etSenha.toString())

        btnEntry.setOnClickListener(){
            if(login == etLogin.text.toString() && senha == etSenha.text.toString()){
                startActivity(Intent(this, MainActivityAdm::class.java))
            }else{
                Toast.makeText(this, "Login ou Senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }
        btnBack.setOnClickListener{
            startActivity((Intent(this, MainActivity::class.java)))
        }
    }
}