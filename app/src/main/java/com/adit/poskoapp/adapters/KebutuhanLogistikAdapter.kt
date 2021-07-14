package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.CreateOrUpdateKebutuhanActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.models.KebutuhanLogistik
import kotlinx.android.synthetic.main.list_item_kebutuhan_logistik.view.*

class KebutuhanLogistikAdapter(private var kebutuhan_logistik : List<KebutuhanLogistik>, private var context: Context, private var listener : onCLickAdapterKebutuhan) : RecyclerView.Adapter<KebutuhanLogistikAdapter.MyHolder>() {
    inner class MyHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {
        fun bind(kebutuhan_logistik: KebutuhanLogistik, context: Context){
            itemView.tvTanggal.text = kebutuhan_logistik.tanggal
            itemView.tvKeterangan.text = kebutuhan_logistik.keterangan
            itemView.tvJumlah.text = kebutuhan_logistik.jumlah.toString()
            itemView.tvJenisKebutuhan.text = kebutuhan_logistik.jenis_kebutuhan
            itemView.btnEdit.setOnClickListener {
                val intent = Intent(context, CreateOrUpdateKebutuhanActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("KEBUTUHAN", kebutuhan_logistik)
                }

                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KebutuhanLogistikAdapter.MyHolder {
        return  MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_kebutuhan_logistik, parent, false))
    }

    override fun onBindViewHolder(holder: KebutuhanLogistikAdapter.MyHolder, position: Int) {
        holder.bind(kebutuhan_logistik[position], context)
        holder.itemView.btnHapus.setOnClickListener {
            listener.deleteData(kebutuhan_logistik[position])
        }
    }

    override fun getItemCount() = kebutuhan_logistik.size
}

interface onCLickAdapterKebutuhan{
    fun deleteData(kebutuhan_logistik: KebutuhanLogistik)
}