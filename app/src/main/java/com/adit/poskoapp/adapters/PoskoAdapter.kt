package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Posko
import kotlinx.android.synthetic.main.list_item_posko.view.*

class PoskoAdapter(private var data: List<Posko>, private var context: Context) :
    RecyclerView.Adapter<PoskoAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_posko, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) =
        holder.bind(data[position], context)

    override fun getItemCount() = data.size

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(posko: Posko, context: Context) {
            itemView.tvNamaPosko.text = posko.nama
            itemView.tvPengungsi.text = posko.jumlah_pengungsi
            itemView.tvLokasi.text = posko.lokasi

        }

    }
}
