package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.CreateOrUpdateDonaturActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Donatur
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.list_item_donatur.view.*
import kotlinx.android.synthetic.main.list_item_donatur.view.btnEdit
import kotlinx.android.synthetic.main.list_item_donatur.view.btnHapus
import kotlinx.android.synthetic.main.list_item_donatur.view.linearButton
import kotlinx.android.synthetic.main.list_item_donatur.view.tvJenisKebutuhan
import kotlinx.android.synthetic.main.list_item_donatur.view.tvKeterangan
import kotlinx.android.synthetic.main.list_item_donatur.view.tvTanggal

class DonaturAdapter(private var data_donatur : List<Donatur>, private var context : Context, private var listener : onClickAdapterDonatur): RecyclerView.Adapter<DonaturAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data_donatur: Donatur, context: Context){
            itemView.tvNama.text = data_donatur.nama
            itemView.tvPoskoPenerima.text = data_donatur.id_posko
            itemView.tvJenisKebutuhan.text = data_donatur.jenis_kebutuhan
            itemView.tvKeterangan.text = data_donatur.keterangan
            itemView.tvAlamat.text = data_donatur.alamat
            itemView.tvTanggal.text = data_donatur.tanggal
            itemView.btnEdit.setOnClickListener {
                val intent = Intent(context, CreateOrUpdateDonaturActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("DONATUR", data_donatur)
                }
                context.startActivity(intent)
            }

            val token = PoskoUtils.getToken(context)
            if(token == null || token.equals("UNDEFINED")){
                itemView.linearButton.apply {
                    visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_donatur, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(data_donatur[position], context)
        holder.itemView.btnHapus.setOnClickListener {
            listener.deleteData(data_donatur[position])
        }
    }

    override fun getItemCount(): Int = data_donatur.size
}

interface onClickAdapterDonatur{
    fun deleteData(data_donatur: Donatur)
}

