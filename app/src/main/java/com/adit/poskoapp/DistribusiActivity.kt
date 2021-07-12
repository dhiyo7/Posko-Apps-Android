package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adit.poskoapp.databinding.ActivityDistribusiBinding

class DistribusiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDistribusiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDistribusiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent()
    }

    private fun intent(){
        binding.logistikMasuk.setOnClickListener {
            startActivity(Intent(this@DistribusiActivity, LogistikMasukActivity::class.java))
        }

        binding.logistikKeluar.setOnClickListener {
            Toast.makeText(this@DistribusiActivity, "Under Development", Toast.LENGTH_LONG).show()
        }
    }
}