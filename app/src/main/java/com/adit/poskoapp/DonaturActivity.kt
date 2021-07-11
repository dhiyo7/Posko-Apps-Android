package com.adit.poskoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.DonaturAdapter
import com.adit.poskoapp.contracts.DonaturActivityContract
import com.adit.poskoapp.databinding.ActivityDonaturBinding
import com.adit.poskoapp.models.Donatur
import com.adit.poskoapp.presenters.DonaturActivityPresenter
import kotlinx.android.synthetic.main.activity_donatur.*
import kotlinx.android.synthetic.main.content_donatur.*

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
    }

    override fun showToast(message: String) {
        Toast.makeText(this@DonaturActivity, message, Toast.LENGTH_LONG).show();
    }

    override fun attachDonaturRecycler(data_donatur: List<Donatur>) {
        donaturAdapter = DonaturAdapter(data_donatur, this)
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

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    override fun onResume() {
        super.onResume()
        infoDonatur()
    }
}