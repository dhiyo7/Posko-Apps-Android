package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.PenerimaanLogistikAdapter
import com.adit.poskoapp.adapters.onClickAdapterPenerimaan
import com.adit.poskoapp.contracts.PenerimaanLogisitkContract
import com.adit.poskoapp.databinding.ActivityPenerimaanLogistikBinding
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.presenters.PenerimaanLogistikActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
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
        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this@PenerimaanLogistikActivity, CreateOrUpdatePenerimaanActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }).also{
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun attachPenerimaanLogistikRecycler(data_penerimaan_logistik: List<PenerimaanLogistik>) {
        penerimaanLogisitikAdapter = PenerimaanLogistikAdapter(data_penerimaan_logistik,this, object: onClickAdapterPenerimaan{
            override fun edit(data_penerimaan_logistik: PenerimaanLogistik) {
                val intent = Intent(this@PenerimaanLogistikActivity, CreateOrUpdatePenerimaanActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("PENERIMAAN", data_penerimaan_logistik)
                }

                startActivity(intent).also {
                    finish()
                }
            }

            override fun deleteData(data_penerimaan_logistik: PenerimaanLogistik) {
                deleteData(data_penerimaan_logistik!!.id.toString())
            }

        })
        rvPenerimaanLogistik.apply {
            layoutManager = LinearLayoutManager(this@PenerimaanLogistikActivity)
            adapter = penerimaanLogisitikAdapter
        }
    }

    private fun deleteData(id : String){
        val token = PoskoUtils.getToken(this)
        presenter?.delete(token!!, id)
        startActivity(Intent(this@PenerimaanLogistikActivity, PenerimaanLogistikActivity::class.java).also{
            finish()
        })
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