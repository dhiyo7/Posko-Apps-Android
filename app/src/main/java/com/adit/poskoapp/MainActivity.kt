package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_main)

        bencana.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, BencanaActivity::class.java))
        })
        posko.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PoskoActivity::class.java))
        })
        petugas.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PetugasActivity::class.java))
        })
        donatur.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, DonaturActivity::class.java))
        })
    }
}