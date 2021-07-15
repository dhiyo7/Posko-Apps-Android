package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.LogistikMasukAdapter
import com.adit.poskoapp.adapters.onClickAdapterLogistikMasuk
import com.adit.poskoapp.contracts.LogistikMasukActivityContract
import com.adit.poskoapp.databinding.ActivityLogistikMasukBinding
import com.adit.poskoapp.models.LogistikMasuk
import com.adit.poskoapp.presenters.LogistikMasukActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.content_logistik_masuk.*

class LogistikMasukActivity : AppCompatActivity(), LogistikMasukActivityContract.LogistikMasukActivityView {
    private lateinit var binding : ActivityLogistikMasukBinding
    private lateinit var logistikMasukAdapter : LogistikMasukAdapter
    private var presenter : LogistikMasukActivityContract.LogistikMasukPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogistikMasukBinding.inflate(layoutInflater)
        presenter = LogistikMasukActivityPresenter(this)
        setSupportActionBar(findViewById(R.id.toolbarLogistikMasuk))
        binding.toolbarLayoutLogistikMasuk.title = "Logistik Masuk"
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this@LogistikMasukActivity, CreateOrUpdateLogistikMasukActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }).also{
                finish()
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LogistikMasukActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachLogistikMasukRecycler(logistik_masuk: List<LogistikMasuk>) {
        logistikMasukAdapter = LogistikMasukAdapter(logistik_masuk, this, object : onClickAdapterLogistikMasuk{
            override fun edit(logistik_masuk: LogistikMasuk) {
                val intent = Intent(this@LogistikMasukActivity, CreateOrUpdateLogistikMasukActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("LOGISTIK_MASUK", logistik_masuk)
                }
                startActivity(intent).also {
                    finish()
                }
            }

            override fun delete(logistik_masuk: LogistikMasuk) {
                delete(logistik_masuk.id!!.toInt())
            }

        })
        rvLogistikMasuk.apply {
            layoutManager = LinearLayoutManager(this@LogistikMasukActivity)
            adapter = logistikMasukAdapter
        }
    }

    override fun showLoading() {
        binding.loadingLogistikMasuk.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loadingLogistikMasuk.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun showDataEmpty() {
        binding.emptyDataLogistikMasuk.apply {
            text = "Tidak ada data"
            visibility = View.VISIBLE
        }
    }

    override fun hideDataEmpty() {
        binding.emptyDataLogistikMasuk.apply {
            visibility = View.GONE
        }
    }

    private fun delete(id : Int){
        val token = PoskoUtils.getToken(this)
        presenter?.delete(token!!, id)
    }

    private fun getLogistikMasuk(){
        val token = PoskoUtils.getToken(this)
        presenter?.getLogistikMasuk(token!!)
    }

    override fun onResume() {
        super.onResume()
        getLogistikMasuk()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}