package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.LogistikKeluar
import kotlinx.android.synthetic.main.list_item_logistik_keluar.view.*

class LogistikKeluarAdapter(private var logistik_keluar : List<LogistikKeluar>, private var context: Context, private var listener : onClickLogistikKeluarAdapter) : RecyclerView.Adapter<LogistikKeluarAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(logistik_keluar: LogistikKeluar, context: Context){
            itemView.tvPoskoPenerima.text = logistik_keluar.posko_penerima
            itemView.tvJenisKebutuhan.text = logistik_keluar.jenis_kebutuhan
            itemView.tvKeterangan.text = logistik_keluar.keterangan
            itemView.tvJumlah.text = logistik_keluar.jumlah
            itemView.tvTanggal.text = logistik_keluar.tanggal
            itemView.tvSatuan.text = logistik_keluar.satuan
            itemView.tvStatus.text = logistik_keluar.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_logistik_keluar, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(logistik_keluar[position], context)
        holder.itemView.btnEdit.setOnClickListener {
            listener.edit(logistik_keluar[position])
        }

        holder.itemView.btnHapus.setOnClickListener {
            listener.delete(logistik_keluar[position])
        }
    }

    override fun getItemCount() = logistik_keluar.size
}

interface onClickLogistikKeluarAdapter {
    fun edit(logistik_keluar: LogistikKeluar)
    fun delete(logistik_keluar: LogistikKeluar)
}
