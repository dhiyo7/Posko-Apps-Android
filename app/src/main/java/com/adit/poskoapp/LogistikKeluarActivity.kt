package com.adit.poskoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.LogistikKeluarAdapter
import com.adit.poskoapp.adapters.onClickLogistikKeluarAdapter
import com.adit.poskoapp.contracts.LogistikKeluarActivityContract
import com.adit.poskoapp.databinding.ActivityLogistikKeluarBinding
import com.adit.poskoapp.models.LogistikKeluar
import com.adit.poskoapp.presenters.LogistikKeluarActivityPresenter
import com.adit.poskoapp.presenters.LogistikMasukActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.content_logistik_keluar.*

class LogistikKeluarActivity : AppCompatActivity(), LogistikKeluarActivityContract.LogistikKeluarActivityView {

    private lateinit var binding : ActivityLogistikKeluarBinding
    private var presenter : LogistikKeluarActivityContract.LogistikKeluarPresenter? = null
    private lateinit var logistikKeluarAdapter : LogistikKeluarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogistikKeluarBinding.inflate(layoutInflater)
        presenter = LogistikKeluarActivityPresenter(this)
        setSupportActionBar(findViewById(R.id.toolbarLogistikMasuk))
        binding.toolbarLayoutLogistikKeluar.title = "Logistik Keluar"
        setContentView(binding.root)
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LogistikKeluarActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachLogistikKeluarRecycler(logistik_keluar: List<LogistikKeluar>) {
        logistikKeluarAdapter = LogistikKeluarAdapter(logistik_keluar, this@LogistikKeluarActivity, object: onClickLogistikKeluarAdapter{
            override fun edit(logistik_keluar: LogistikKeluar) {
                TODO("Not yet implemented")
            }

            override fun delete(logistik_keluar: LogistikKeluar) {
                TODO("Not yet implemented")
            }

        })
        rvLogistikKeluar.apply {
            layoutManager = LinearLayoutManager(this@LogistikKeluarActivity)
            adapter = logistikKeluarAdapter

        }
    }

    override fun showLoading() {
        binding.loadingLogistikKeluar.isIndeterminate = true
    }

    override fun hideLoading() {
        binding.loadingLogistikKeluar.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun showDataEmpty() {
        binding.emptyDataLogistikKeluar.apply {
            visibility = View.VISIBLE
        }
    }

    override fun hideDataEmpty() {
        binding.emptyDataLogistikKeluar.apply {
            visibility = View.GONE
        }
    }

    private fun getData(){
        val token = PoskoUtils.getToken(this)
        presenter?.getLogistikKeluar(token!!)
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