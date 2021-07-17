package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.CreateAndUpdatePetugasActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Petugas
import kotlinx.android.synthetic.main.list_item_petugas.view.*

class PetugasAdapter(private var petugas: List<Petugas>, private var context: Context, private var onClickAdapter: onClickAdapter): RecyclerView.Adapter<PetugasAdapter.MyHolder>(), Filterable {
    private var filterList = ArrayList<Petugas>()

    init {
        filterList = petugas as ArrayList<Petugas>
    }

    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(petugas : Petugas, context: Context){
            itemView.tvId.text = petugas.id.toString()
            itemView.tvIdPosko.text = petugas.id_posko.toString()
            itemView.tvUsername.text = petugas.username
            itemView.tvLevel.text = petugas.level
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.list_item_petugas, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int){
        holder.itemView.btnHapus.setOnClickListener {
            onClickAdapter.showDialog(petugas[position])
        }

        holder.itemView.btnEdit.setOnClickListener {
            onClickAdapter.edit(petugas[position])
        }
    }

    override fun getItemCount() = petugas.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                TODO("Not yet implemented")
            }

        }
    }
}

interface onClickAdapter{
    fun edit(petugas: Petugas)
    fun showDialog(petugas: Petugas)
}