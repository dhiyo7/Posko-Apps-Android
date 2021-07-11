package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import com.adit.poskoapp.databinding.ActivityMainBinding
import com.adit.poskoapp.fragments.*
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
        moveFragment()
//        getLevel()

    }

    private fun getLevel() : String? {
        val level = PoskoUtils.getLevel(this)
        return level
    }

    private fun moveFragment() {
        if(getLevel() == "Admin"){
            setFragment(BerandaAdminFragment())
        }else if(getLevel() == "Petugas"){
            setFragment(BerandaPetugasFragment())
        }else{
            setFragment(BerandaFragment())
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    if(getLevel() == "Admin"){
                        setFragment(BerandaAdminFragment())
                    }else if(getLevel() == "Petugas"){
                        setFragment(BerandaPetugasFragment())
                    }else{
                        setFragment(BerandaFragment())
                    }
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