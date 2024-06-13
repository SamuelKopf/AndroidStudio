package com.example.atividadesala

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teste.adapter.AdapterFilmes
import com.example.teste.model.Obra
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivityVisit : AppCompatActivity() {

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_visit)

        val scanBtn = findViewById<ImageButton>(R.id.imageButtonScan)
        scanBtn.setOnClickListener {
            IntentIntegrator(this).initiateScan() // Inicia o scanner de QR code
        }

        var btnBack = findViewById<Button>(R.id.buttonBackMain2)
        btnBack.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

        var btnAval = findViewById<ImageButton>(R.id.imageButtonAval)
        btnAval.setOnClickListener(){
            startActivity(Intent(this, MainActivityAval::class.java))
        }

        var btnImg = findViewById<ImageButton>(R.id.imageButtonScan)



        getDataBase()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        var bundle = intent.extras

        if (result != null) {
            if (result.contents == null) {
                // O usuário cancelou o escaneamento
            } else {
                // QR Code escaneado com sucesso
                val scannedData = result.contents
                // Faça algo com o resultado (exibir ou processar)
                println(scannedData)
                var intent: Intent
                intent =  Intent(this, MainActivityObra::class.java)

                intent.putExtra("id", scannedData)

                startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun getDataBase(){
        var listObras = ArrayList<Obra>()
        Firebase.firestore.collection("Obras").get()
            .addOnSuccessListener {
                    result ->
                for (document in result){
                    listObras.add(
                        Obra(
                            document.id.toString(),
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