package com.adit.poskoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.PenyaluranAdapter
import com.adit.poskoapp.adapters.onClickPenyaluranAdapter
import com.adit.poskoapp.contracts.PenyaluranActivityContract
import com.adit.poskoapp.databinding.ActivityPenyaluranBinding
import com.adit.poskoapp.models.Penyaluran
import com.adit.poskoapp.presenters.PenyaluranActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.activity_penyaluran.*

class PenyaluranActivity : AppCompatActivity(), PenyaluranActivityContract.PenyaluranActivityView {

    private lateinit var binding: ActivityPenyaluranBinding
    private var presenter : PenyaluranActivityContract.PenyaluranActivityPresenter? = null
    private lateinit var adapterPenyaluran : PenyaluranAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PenyaluranActivityPresenter(this)
        binding = ActivityPenyaluranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this@PenyaluranActivity, CreateOrUpdatePenyaluranActivity::class.java).apply{
                putExtra("IS_NEW", true)
            }).also{
                finish()
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PenyaluranActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachPenyaluranRecycler(penyaluran: List<Penyaluran>) {
        adapterPenyaluran = PenyaluranAdapter(penyaluran, this@PenyaluranActivity, object: onClickPenyaluranAdapter{
            override fun edit(penyaluran: Penyaluran) {
                val intent = Intent(this@PenyaluranActivity, CreateOrUpdatePenyaluranActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("PENYALURAN", penyaluran)
                }
                startActivity(intent).also { finish() }
            }

            override fun delete(penyaluran: Penyaluran) {
                delete(penyaluran.id)
            }
        })

        rvPenyaluran.apply {
            layoutManager = LinearLayoutManager(this@PenyaluranActivity)
            adapter = adapterPenyaluran
        }
    }

    override fun showLoading() {
        binding.loadingPenyaluran.isIndeterminate = true
    }

    override fun hideLoading() {
        binding.loadingPenyaluran.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun showDataEmpty() {
        binding.emptyData.text = "Tidak ada data"
        binding.emptyData.visibility = View.VISIBLE
        println("Tidak ada Data")
    }

    override fun hideDataEmpty() {
        binding.emptyData.apply {
            visibility = View.GONE
        }
    }

    private fun getData(){
        val token = PoskoUtils.getToken(this)
        presenter?.infoPenyaluran(token!!)
    }

    private fun delete(id : String){
        val token = PoskoUtils.getToken(this)
        presenter?.delete(token!!, id)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}