package com.example.atividadesala

import android.graphics.BitmapFactory
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Base64
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class MainActivityObra : AppCompatActivity() {

    lateinit var mTextToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_obra)

        var btnBack = findViewById<Button>(R.id.buttonBack)
        var nomeObra = findViewById<TextView>(R.id.textViewNomeObra)
        var autor = findViewById<TextView>(R.id.textViewAutor)
        var origem = findViewById<TextView>(R.id.textViewOrigem)
        var desc = findViewById<TextView>(R.id.textViewDesc)
        var img = findViewById<ImageView>(R.id.imageViewObra)

        btnBack.setOnClickListener() {
            onBackPressed()
        }

        var bundle = intent.extras
        var id = bundle!!.getString("id")

        Firebase.firestore.collection("Obras").get().addOnSuccessListener {
        result ->
            for (document in result){
               if(document.id == id){
                   var basebyte = Base64.decode(document.get("imagem").toString(), 0)
                   img.setImageBitmap(BitmapFactory.decodeByteArray(basebyte, 0, basebyte.size))
                   nomeObra.text = document.get("nomeObra").toString() + " - " + document.get("ano").toString()
                   autor.text = document.get("autor").toString()
                   origem.text = document.get("origem").toString()
                   desc.text = document.get("desc").toString()

               }

            }
        }

        mTextToSpeech = TextToSpeech(this) {
            mTextToSpeech.language = Locale.ITALY
        }
        var btnSpeak = findViewById<ImageButton>(R.id.imageButton)
        btnSpeak.setOnClickListener {
            OnSpeech(desc)
        }
    }

    fun OnSpeech(desc: TextView) {
        mTextToSpeech.speak(desc.text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

}