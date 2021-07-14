package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.CreateOrUpdatePenerimaanActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.list_item_penerimaan_logistik.view.*

class PenerimaanLogistikAdapter(private var data_penerimaan_logistik : List<PenerimaanLogistik>, private var context : Context, private var listener : onClickAdapterPenerimaan) : RecyclerView.Adapter<PenerimaanLogistikAdapter.MyHolder>(){
    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data_penerimaan_logistik: PenerimaanLogistik, context: Context){
            itemView.tvJenisKebutuhan.text = data_penerimaan_logistik.jenis_kebutuhan
            itemView.tvJumlah.text = data_penerimaan_logistik.jumlah.toString()
            itemView.tvKeterangan.text = data_penerimaan_logistik.keterangan
            itemView.tvTanggal.text = data_penerimaan_logistik.tanggal
            itemView.btnEdit.setOnClickListener {
                val intent = Intent(context, CreateOrUpdatePenerimaanActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("PENERIMAAN", data_penerimaan_logistik)
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
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_penerimaan_logistik, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(data_penerimaan_logistik[position], context)
        holder.itemView.btnHapus.setOnClickListener { 
            listener.deleteData(data_penerimaan_logistik[position])
        }
    }

    override fun getItemCount() = data_penerimaan_logistik.size

}

interface onClickAdapterPenerimaan{
    fun deleteData(data_penerimaan_logistik: PenerimaanLogistik)
}