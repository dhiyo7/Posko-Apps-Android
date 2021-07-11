package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import com.adit.poskoapp.databinding.ActivityMainBinding
import com.adit.poskoapp.fragments.BerandaFragment
import com.adit.poskoapp.fragments.PemberitahuanFragment
import com.adit.poskoapp.fragments.ProfileFragment
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar()?.hide();

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        moveFragment()


//        bencana.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(this, BencanaActivity::class.java))
//        })
//        posko.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(this, PoskoActivity::class.java))
//        })
//        petugas.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(this, PetugasActivity::class.java))
//        })
//        donatur.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(this, DonaturActivity::class.java))
//        })
//
//        btnLogout.setOnClickListener {
//            PoskoUtils.clearToken(this@MainActivity)
//            checkAuthenticated()
//        }
    }

    private fun moveFragment() {
        setFragment(BerandaFragment())
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    setFragment(BerandaFragment())
                }
                R.id.menu_notifikasi -> {
                    setFragment(PemberitahuanFragment())
                }
                R.id.menu_profil -> {
                    setFragment(ProfileFragment())
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }
}