package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.CreateAndUpdatePetugasActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Petugas
import kotlinx.android.synthetic.main.list_item_petugas.view.*

class PetugasAdapter(private var petugas: List<Petugas>, private var context: Context, private var onClickAdapter: onClickAdapter): RecyclerView.Adapter<PetugasAdapter.MyHolder>() {
    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(petugas : Petugas, context: Context){
            itemView.tvId.text = petugas.id.toString()
            itemView.tvIdPosko.text = petugas.id_posko.toString()
            itemView.tvNama.text = petugas.nama
            itemView.tvUsername.text = petugas.username
            itemView.tvLevel.text = petugas.level
            itemView.btnEdit.setOnClickListener {
                val intent = Intent(context, CreateAndUpdatePetugasActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("PETUGAS", petugas)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_petugas, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int){
        holder.bind(petugas[position], context)
        holder.itemView.btnHapus.setOnClickListener {
            onClickAdapter.showDialog(petugas[position])
        }
    }

    override fun getItemCount() = petugas.size
}

interface onClickAdapter{
    fun showDialog(petugas: Petugas)
}