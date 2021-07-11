package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Donatur
import kotlinx.android.synthetic.main.list_item_donatur.view.*

class DonaturAdapter(private var data_donatur : List<Donatur>, private var context : Context): RecyclerView.Adapter<DonaturAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data_donatur: Donatur, context: Context){
            itemView.tvNamaDonatur.text = data_donatur.nama
            itemView.tvAlamatDonatur.text = data_donatur.alamat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_donatur, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) = holder.bind(data_donatur[position], context)

    override fun getItemCount(): Int = data_donatur.size
}