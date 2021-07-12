package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.LogistikMasuk
import kotlinx.android.synthetic.main.list_item_logistik_masuk.view.*

class LogistikMasukAdapter(private var logistik_masuk : List<LogistikMasuk>, private var context: Context) : RecyclerView.Adapter<LogistikMasukAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(logistik_masuk : LogistikMasuk, context: Context){
            itemView.tvJenisKebutuhan.text = logistik_masuk.jenis_kebutuhan
            itemView.tvJenisKebutuhan.text = logistik_masuk.jumlah
            itemView.tvPengirim.text = logistik_masuk.pengirim
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_logistik_masuk, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) = holder.bind(logistik_masuk[position], context)

    override fun getItemCount() = logistik_masuk.size
}