package com.adit.poskoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Petugas
import kotlinx.android.synthetic.main.list_item_petugas.view.*

class PetugasAdapter(private var petugas: ArrayList<Petugas>, private var context: Context, private var onClickAdapter: onClickAdapter): RecyclerView.Adapter<PetugasAdapter.MyHolder>(), Filterable{
    private var petugasList = petugas
    private var petugasFull = ArrayList<Petugas>(petugasList)


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
        holder.bind(petugasList[position], context)

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
                var filteredList  = ArrayList<Petugas>()

                if(constraint == null || constraint.length == 0){
                    filteredList.addAll(petugasFull)
                }else{
                    var filterPattern : String = constraint.toString().lowercase().trim()

                    for(item in petugasFull){
                        if(item.username?.lowercase()!!.contains(filterPattern)){
                            filteredList.add(item)
                        }
                    }
                }
                var results : FilterResults = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                petugasList.clear()
                petugasList.addAll(results?.values as ArrayList<Petugas>)
                notifyDataSetChanged()
            }

        }
    }

}

interface onClickAdapter{
    fun edit(petugas: Petugas)
    fun showDialog(petugas: Petugas)
}