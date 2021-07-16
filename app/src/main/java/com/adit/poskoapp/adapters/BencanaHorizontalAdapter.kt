package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adit.poskoapp.DetailBencanaActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Bencana
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.list_bencana_horizontal.view.*

class BencanaHorizontalAdapter(private var bencana : List<Bencana>, private var context : Context) : SliderViewAdapter<BencanaHorizontalAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        fun bind(bencana : Bencana, context: Context){
            itemView.tvAutoImage.text= bencana.nama
            itemView.ivImage.load(bencana.foto)
            itemView.setOnClickListener {
                val intent = Intent(context, DetailBencanaActivity::class.java).apply{
                    putExtra("BENCANA", bencana)
                }

                context.startActivity(intent)
            }
        }
    }

    override fun getCount() = bencana.size

    override fun onCreateViewHolder(parent: ViewGroup?): MyHolder {
        return MyHolder(LayoutInflater.from(parent?.context).inflate(R.layout.list_bencana_horizontal, null))
    }

    override fun onBindViewHolder(viewHolder: MyHolder?, position: Int) = viewHolder!!.bind(bencana[position], context)
}