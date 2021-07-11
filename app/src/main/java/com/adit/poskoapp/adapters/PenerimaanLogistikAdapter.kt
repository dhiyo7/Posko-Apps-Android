package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.PenerimaanLogistik
import kotlinx.android.synthetic.main.list_item_penerimaan_logistik.view.*

class PenerimaanLogistikAdapter(private var data_penerimaan_logistik : List<PenerimaanLogistik>, private var context : Context) : RecyclerView.Adapter<PenerimaanLogistikAdapter.MyHolder>(){
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data_penerimaan_logistik: PenerimaanLogistik, context: Context){
            itemView.tvJenisKebutuhan.text = data_penerimaan_logistik.jenis_kebutuhan
            itemView.tvJumlahPenerimaanLogistik.text = data_penerimaan_logistik.jumlah.toString()
            itemView.tvKeterangaPenerimaanLogistik.text = data_penerimaan_logistik.keterangan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_penerimaan_logistik, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) = holder.bind(data_penerimaan_logistik[position], context)

    override fun getItemCount() = data_penerimaan_logistik.size

}