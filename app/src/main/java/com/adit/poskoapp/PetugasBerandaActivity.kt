package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.activity_main.*

class PetugasBerandaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petugas_beranda)
//        btnLogout.setOnClickListener {
//            PoskoUtils.clearToken(this@PetugasBerandaActivity)
//            checkAuthenticated()
//        }
    }

    private fun checkAuthenticated(){
        val token = PoskoUtils.getToken(this@PetugasBerandaActivity)
        if (token == null || token.equals("UNDEFINED")){
            startActivity(Intent(this@PetugasBerandaActivity, LoginActivity::class.java).also { finish() })
        }
    }
}