package com.example.atividadesala

import android.content.Intent
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

class MainActivityObraAdm : AppCompatActivity() {

    lateinit var mTextToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_obra_adm)

        var btnBack = findViewById<Button>(R.id.buttonBack)
        var nomeObra = findViewById<TextView>(R.id.textViewNomeObra)
        var autor = findViewById<TextView>(R.id.textViewAutor)
        var origem = findViewById<TextView>(R.id.textViewOrigem)
        var desc = findViewById<TextView>(R.id.textViewDesc)
        var img = findViewById<ImageView>(R.id.imageViewObra)
        var btnEdit = findViewById<Button>(R.id.buttonEdit)

        btnBack.setOnClickListener() {
            onBackPressed()
        }


        var bundle = intent.extras
        var id = bundle!!.getString("id","0")

        Firebase.firestore.collection("Obras").get().addOnSuccessListener {
                result ->
            for (document in result){
                if(document.id == id){
                    var basebyte = Base64.decode(document.get("imagem").toString(), 0)
                    img.setImageBitmap(BitmapFactory.decodeByteArray(basebyte, 0, basebyte.size))
                }

            }
        }
        nomeObra.text = bundle!!.getString("nomeObra") + (" - ") + bundle!!.getString("ano")
        autor.text = bundle!!.getString("autor")
        origem.text = bundle!!.getString("origem")
        desc.text = bundle!!.getString("desc")

        btnEdit.setOnClickListener(){


            var intent =  Intent(this, MainActivityEdit::class.java)

            intent.putExtra("id", id)
            intent.putExtra("nomeObra", bundle!!.getString("nomeObra"))
            intent.putExtra("autor", bundle!!.getString("autor"))
            intent.putExtra("origem", bundle!!.getString("origem"))
            intent.putExtra("ano", bundle!!.getString("ano"))
            intent.putExtra("desc", bundle!!.getString("desc"))

            startActivity(intent)


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