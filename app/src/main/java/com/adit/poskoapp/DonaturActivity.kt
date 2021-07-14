package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.DonaturAdapter
import com.adit.poskoapp.adapters.onClickAdapterDonatur
import com.adit.poskoapp.contracts.DonaturActivityContract
import com.adit.poskoapp.databinding.ActivityDonaturBinding
import com.adit.poskoapp.models.Donatur
import com.adit.poskoapp.presenters.DonaturActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.activity_donatur.*
import kotlinx.android.synthetic.main.content_donatur.*
import kotlinx.android.synthetic.main.list_item_donatur.view.*

class DonaturActivity : AppCompatActivity(), DonaturActivityContract.DonaturActivityView {
    private lateinit var binding : ActivityDonaturBinding

    private var presenter : DonaturActivityContract.DonaturActivityPresenter? = null
    private lateinit var donaturAdapter : DonaturAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonaturBinding.inflate(layoutInflater)
        presenter = DonaturActivityPresenter(this)
        setSupportActionBar(findViewById(R.id.toolbarDonatur))
        binding.toolbarLayoutDonatur.title = "Daftar Donatur"
        setContentView(binding.root)

        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this@DonaturActivity, CreateOrUpdateDonaturActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }).also {
                finish()
            }
        }

        hideButtonWhenNotAuth()
    }

    private fun hideButtonWhenNotAuth(){
        val token = PoskoUtils.getToken(this)
        if(token == null || token.equals("UNDEFINED")){
            binding.fab.visibility = View.GONE
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@DonaturActivity, message, Toast.LENGTH_LONG).show();
    }

    override fun attachDonaturRecycler(data_donatur: List<Donatur>) {
        donaturAdapter = DonaturAdapter(data_donatur, this, object : onClickAdapterDonatur{
            override fun deleteData(data_donatur: Donatur) {
                delete(data_donatur.id.toString())
            }

        })
        rvDonatur.apply {
            adapter = donaturAdapter
            layoutManager = LinearLayoutManager(this@DonaturActivity)
        }
    }

    override fun showLoading() {
        loadingDonatur.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        loadingDonatur.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun showDataEmpty() {
        binding.emptyDataDonatur.text = "Tidak ada data"
        binding.emptyDataDonatur.visibility = View.VISIBLE
        println("Tidak ada Data")
    }

    override fun hideDataEmpty() {
        emptyDataDonatur.apply {
            visibility = View.GONE
        }
    }

    private fun infoDonatur() = presenter?.infoDonatur()

    private fun delete(id : String){
        val token = PoskoUtils.getToken(this)
        presenter?.delete(token!!, id)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    override fun onResume() {
        super.onResume()
        infoDonatur()
    }
}