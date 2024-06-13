package com.example.atividadesala

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainActivityEdit : AppCompatActivity() {

    private val PICK_IMAGE = 1
    private var imageString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_edit)

        var nome_obra = findViewById<EditText>(R.id.editTextNomeObra)
        var autor = findViewById<EditText>(R.id.editTextAutor)
        var ano = findViewById<EditText>(R.id.editTextAno)
        var origem = findViewById<EditText>(R.id.editTextOrigem)
        var desc = findViewById<EditText>(R.id.editTextDesc)
        var btnOpenGallery = findViewById<Button>(R.id.buttonImage)
        var btnSave = findViewById<Button>(R.id.buttonSave)
        var btnDelete = findViewById<Button>(R.id.buttonDelete)
        var btnBack = findViewById<Button>(R.id.buttonBackObra)

        var bundle = intent.extras
        var id = bundle!!.getString("id","0")

        Firebase.firestore.collection("Obras").get().addOnSuccessListener {
                result ->
            for (document in result){
                if(document.id == id){
                    imageString = document.get("imagem").toString()
                }

            }
        }

        nome_obra.setText(bundle!!.getString("nomeObra"))
        autor.setText(bundle!!.getString("autor"))
        ano.setText(bundle!!.getString("ano"))
        origem.setText(bundle!!.getString("origem"))
        desc.setText(bundle!!.getString("desc"))


        btnOpenGallery.setOnClickListener {
            openGallery()
        }

        btnSave.setOnClickListener(){
            Firebase.firestore.collection("Obras")
                .document(bundle!!.getString("id").toString()).update(mapOf(
                    "nomeObra" to nome_obra.text.toString(),
                    "autor" to autor.text.toString(),
                    "ano" to ano.text.toString(),
                    "origem" to origem.text.toString(),
                    "desc" to desc.text.toString(),
                    "imagem" to imageString
                ))
            Toast.makeText(this, "Obra atualizada com sucesso", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivityAdm::class.java))
        }

        btnDelete.setOnClickListener(){
            Firebase.firestore.collection("Obras")
            .document(bundle!!.getString("id").toString()).delete()
            Toast.makeText(this, "Obra deletada com sucesso", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivityAdm::class.java))
        }

        btnBack.setOnClickListener(){
            onBackPressed()
        }



    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectedImageUri?.let {
                val inputStream: InputStream? = contentResolver.openInputStream(selectedImageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val base64Image = encodeImage(bitmap)
                // Aqui você tem sua imagem em formato Base64
                imageString = base64Image
                Toast.makeText(this, "Imagem em Base64: $base64Image", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun encodeImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}