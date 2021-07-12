package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.DetailBencanaActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Bencana
import kotlinx.android.synthetic.main.list_item_bencana.view.*

class BencanaAdapter(private var data: List<Bencana>, private var context: Context) :
    RecyclerView.Adapter<BencanaAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_bencana, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) = holder.bind(data[position],context)

    override fun getItemCount() = data.size


    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bencana: Bencana, context: Context) {
            itemView.tvNamaBencana.text = bencana.nama
            itemView.tvTanggalBencana.text = bencana.date
            itemView.tvDetail.text = bencana.detail
            itemView.setOnClickListener {
                context.startActivity(Intent(context, DetailBencanaActivity::class.java).apply {
                    putExtra("BENCANA", bencana)
                })
            }
        }

    }
}