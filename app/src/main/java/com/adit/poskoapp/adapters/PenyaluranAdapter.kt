package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Penyaluran
import kotlinx.android.synthetic.main.list_item_penyaluran.view.*

class PenyaluranAdapter(private var penyaluran : List<Penyaluran>, private var context : Context, private var listener : onClickPenyaluranAdapter) : RecyclerView.Adapter<PenyaluranAdapter.MyHolder>() {
    inner class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(penyaluran : Penyaluran, context: Context){
            itemView.tvPenerima.text = penyaluran.nama_penerima
            itemView.tvJenisKebutuhan.text = penyaluran.jenis_kebutuhan
            itemView.tvKeterangan.text = penyaluran.keterangan
            itemView.tvJumlah.text = penyaluran.jumlah
            itemView.tvSatuan.text = penyaluran.satuan
            itemView.tvStatus.text = penyaluran.status
            itemView.tvTanggal.text = penyaluran.tanggal
            itemView.ivFoto.load(penyaluran.foto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_penyaluran, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(penyaluran[position], context)
        holder.itemView.btnHapus.setOnClickListener {
            listener.delete(penyaluran[position])
        }
        holder.itemView.btnEdit.setOnClickListener {
            listener.edit(penyaluran[position])
        }
    }

    override fun getItemCount() = penyaluran.size
}

interface onClickPenyaluranAdapter{
    fun edit(penyaluran: Penyaluran)
    fun delete(penyaluran: Penyaluran)
}