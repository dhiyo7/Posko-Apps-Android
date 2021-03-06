package com.adit.poskoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.adit.poskoapp.databinding.ActivityDetailBencanaBinding
import com.adit.poskoapp.models.Bencana

class DetailBencanaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBencanaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseData()
    }

    private fun getDetailBencana() : Bencana? = intent.getParcelableExtra("BENCANA")

    private fun parseData(){
        println(getDetailBencana())
        binding.tvNamaBencana.text = "Nama Bencana : "+getDetailBencana()?.nama
        binding.ivBencana.load(getDetailBencana()?.foto)
        binding.tvTanggalBencana.text = "Tanggal Bencana : "+ getDetailBencana()?.date
        binding.tvDescription.text = getDetailBencana()?.detail
    }
}