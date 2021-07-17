package com.adit.poskoapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskoapp.R
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.models.User
import com.adit.poskoapp.utils.PoskoUtils
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item_posko.view.*
import kotlinx.android.synthetic.main.list_item_posko.view.linearButton


class PoskoAdapter(private var data: List<Posko>, private var context: Context, private var listener : onClickAdapterPosko) :
    RecyclerView.Adapter<PoskoAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_posko, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(data[position], context)
        holder.itemView.btnEdit.setOnClickListener {
            listener.edit(data[position])
        }

        holder.itemView.btnHapus.setOnClickListener {
            listener.delete(data[position])
        }

    }


    override fun getItemCount() = data.size

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(posko: Posko, context: Context) {
            itemView.tvNamaPosko.text = posko.nama
            itemView.tvPengungsi.text = posko.jumlah_pengungsi
            itemView.tvKontak.text = posko.kontak_hp
            itemView.tvLokasi.text= posko.lokasi
            itemView.btnMaps.setOnClickListener {
                val pcc = LatLng(posko.latitude?.toDouble()!!, posko.longitude?.toDouble()!!)
                val map = "http://maps.google.com/maps?q=loc:" + posko.latitude
                    .toString() + "," + posko.longitude.toString() + " (" + posko.nama
                    .toString() + ")"
                val gmmIntentUri: Uri = Uri.parse(map) as Uri
                System.err.println(pcc);
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context.startActivity(mapIntent)
            }

            val token = PoskoUtils.getToken(context)
            if(token == null || token.equals("UNDEFINED")){
                itemView.btnEdit.apply {
                    visibility = View.GONE
                }

                itemView.btnHapus.apply {
                    visibility = View.GONE
                }
            }else{
                val user = PoskoUtils.getList(context)
                val listUser = Gson().fromJson(user, User::class.java)
//                if(posko.id != listUser.id_posko){
//                    itemView.btnEdit.apply {
//                        visibility = View.GONE
//                    }
//
//                    itemView.btnHapus.apply {
//                        visibility = View.GONE
//                    }
//                }

                if(listUser.level != "Admin"){
                    itemView.btnEdit.apply {
                        visibility = View.GONE
                    }

                    itemView.btnHapus.apply {
                        visibility = View.GONE
                    }
                }
            }
        }

    }
}

interface onClickAdapterPosko{
    fun edit(posko: Posko)
    fun delete(posko: Posko)
}