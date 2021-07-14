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
import com.adit.poskoapp.utils.PoskoUtils
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.list_item_posko.view.*
import kotlinx.android.synthetic.main.list_item_posko.view.linearButton


class PoskoAdapter(private var data: List<Posko>, private var context: Context) :
    RecyclerView.Adapter<PoskoAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_posko, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) =
        holder.bind(data[position], context)

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
                itemView.linearButton.apply {
                    visibility = View.GONE
                }
            }
        }

    }
}
