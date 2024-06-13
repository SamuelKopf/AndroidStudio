package com.example.atividadesala

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.adapter.AdapterFilmes
import com.example.teste.model.Obra
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityAdm : AppCompatActivity() {

    var listObras = ArrayList<Obra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_adm)

        getDataBase()


        val btnNew = findViewById<Button>(R.id.buttonNew)
        val btnBack = findViewById<Button>(R.id.buttonBackMain)

        btnNew.setOnClickListener{
            startActivity(Intent(this, MainActivityAdd::class.java))
        }
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
    fun getDataBase(){

        Firebase.firestore.collection("Obras").get()
            .addOnSuccessListener {
                    result ->
                for (document in result){
                   listObras.add(
                       Obra(
                           document.id,
                           document.get("nomeObra").toString(),
                           document.get("imagem").toString(),
                           document.get("autor").toString(),
                           document.get("origem").toString(),
                           document.get("ano").toString(),
                           document.get("desc").toString()

                   )
                   )

                }
                var recyclerViewFilmes =  findViewById<RecyclerView>(R.id.recyclerView)
                recyclerViewFilmes.layoutManager = LinearLayoutManager(this)
                recyclerViewFilmes.setHasFixedSize(true)
                recyclerViewFilmes.adapter = AdapterFilmes(listObras)
            }

        Log.d("content",""+listObras.size)

    }


}