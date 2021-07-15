package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.LogistikMasuk
import kotlinx.android.synthetic.main.list_item_logistik_masuk.view.*

class LogistikMasukAdapter(private var logistik_masuk : List<LogistikMasuk>, private var context: Context, private var listener : onClickAdapterLogistikMasuk) : RecyclerView.Adapter<LogistikMasukAdapter.MyHolder>() {
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(logistik_masuk : LogistikMasuk, context: Context){
            itemView.tvPengirim.text = logistik_masuk.pengirim
            itemView.tvPoskoPenerima.text = logistik_masuk.posko_penerima
            itemView.tvJenisKebutuhan.text = logistik_masuk.jumlah
            itemView.tvKeterangan.text = logistik_masuk.keterangan
            itemView.tvJumlah.text = logistik_masuk.jumlah
            itemView.tvStatus.text = logistik_masuk.status
            itemView.tvTanggal.text = logistik_masuk.tanggal
            itemView.tvSatuan.text = logistik_masuk.satuan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_logistik_masuk, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(logistik_masuk[position], context)
        holder.itemView.btnEdit.setOnClickListener {
            listener.edit(logistik_masuk[position])
        }

        holder.itemView.btnHapus.setOnClickListener {
            listener.delete(logistik_masuk[position])
        }
    }

    override fun getItemCount() = logistik_masuk.size
}

interface onClickAdapterLogistikMasuk{
    fun edit(logistik_masuk: LogistikMasuk)
    fun delete(logistik_masuk: LogistikMasuk)
}