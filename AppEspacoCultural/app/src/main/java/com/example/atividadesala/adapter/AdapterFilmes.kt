package com.example.teste.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.atividadesala.MainActivityAdm
import com.example.atividadesala.R
import com.example.teste.model.Obra
import com.example.atividadesala.MainActivityObra
import com.example.atividadesala.MainActivityObraAdm
import kotlinx.coroutines.newSingleThreadContext


class AdapterFilmes( private val listaFilmes: MutableList<Obra>):
    RecyclerView.Adapter<AdapterFilmes.FilmeViewHolder>() {

    var x:String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        var item = LayoutInflater.from(parent.context).inflate(R.layout.filme_item,parent,false)
        x = parent.context.javaClass.toString()
        return FilmeViewHolder(item)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        holder.txtTituloFilme.text = listaFilmes[position].nomeObra
        var basebyte = Base64.decode(listaFilmes[position].imagem,0)
        holder.img.setImageBitmap(BitmapFactory.decodeByteArray(basebyte,0,basebyte.size))
        holder.img.setOnClickListener {

            var intent: Intent
            if(x == MainActivityAdm::class.java.toString()){
                intent =  Intent(holder.img.context, MainActivityObraAdm::class.java)
            }else{
                intent =  Intent(holder.img.context, MainActivityObra::class.java)
            }
            intent.putExtra("id", listaFilmes[position].id )
            intent.putExtra("nomeObra", listaFilmes[position].nomeObra)
            intent.putExtra("autor", listaFilmes[position].autor)
            intent.putExtra("origem", listaFilmes[position].origem)
            intent.putExtra("ano", listaFilmes[position].ano)
            intent.putExtra("desc", listaFilmes[position].desc)

            holder.img.context.startActivity(intent)
        }

    }



    override fun getItemCount() = listaFilmes.size

    class FilmeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var txtTituloFilme = itemView.findViewById<TextView>(R.id.txtTituloFilme)
        var img = itemView.findViewById<ImageButton>(R.id.imageButtonAdm)

    }


}