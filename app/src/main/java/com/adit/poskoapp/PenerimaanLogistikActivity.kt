package com.adit.poskoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.PenerimaanLogistikAdapter
import com.adit.poskoapp.contracts.PenerimaanLogisitkContract
import com.adit.poskoapp.databinding.ActivityPenerimaanLogistikBinding
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.presenters.PenerimaanLogistikActivityPresenter
import kotlinx.android.synthetic.main.activity_penerimaan_logistik.*
import kotlinx.android.synthetic.main.content_penerimaan_logistik.*

class PenerimaanLogistikActivity : AppCompatActivity(), PenerimaanLogisitkContract.PenerimaanLogistikActivityView {
    private lateinit var binding : ActivityPenerimaanLogistikBinding
    private var presenter : PenerimaanLogisitkContract.PenerimmaanLogistikPresenter? = null
    private lateinit var  penerimaanLogisitikAdapter : PenerimaanLogistikAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenerimaanLogistikBinding.inflate(layoutInflater)
        presenter = PenerimaanLogistikActivityPresenter(this)

        setSupportActionBar(findViewById(R.id.toolbarPenerimaanLogistik))
        binding.toolbarLayoutPenerimaanLogistik.title = "Daftar Penerimaan Logistik"
        setContentView(binding.root)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun attachPenerimaanLogistikRecycler(data_penerimaan_logistik: List<PenerimaanLogistik>) {
        penerimaanLogisitikAdapter = PenerimaanLogistikAdapter(data_penerimaan_logistik,this)
        rvPenerimaanLogistik.apply {
            layoutManager = LinearLayoutManager(this@PenerimaanLogistikActivity)
            adapter = penerimaanLogisitikAdapter
        }
    }

    override fun showLoading() {
        binding.loadingPenerimaanLogistik.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loadingPenerimaanLogistik.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun showDataEmpty() {
        binding.emptyDataPenerimaanLogistik.text = "Tidak ada data"
        binding.emptyDataPenerimaanLogistik.visibility = View.VISIBLE
        println("TIDAK ADA DATA")
    }

    override fun hideDataEmpty() {
        binding.emptyDataPenerimaanLogistik.apply {
            visibility = View.GONE
        }
    }

    private fun infoPenerimaanLogistik() = presenter?.infoPenerimaanLogistik()

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    override fun onResume() {
        super.onResume()
        infoPenerimaanLogistik()
    }
}