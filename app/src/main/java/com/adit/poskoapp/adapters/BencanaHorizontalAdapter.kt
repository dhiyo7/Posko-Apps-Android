package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Bencana
import kotlinx.android.synthetic.main.list_bencana_horizontal.view.*

class BencanaHorizontalAdapter(private var bencana : List<Bencana>, private var context : Context) : RecyclerView.Adapter<BencanaHorizontalAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bencana : Bencana, context: Context){
            itemView.tv_nama.text = bencana.nama
            itemView.iv_foto.load(bencana.foto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_bencana_horizontal, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) = holder.bind(bencana[position], context)

    override fun getItemCount() = bencana.size
}