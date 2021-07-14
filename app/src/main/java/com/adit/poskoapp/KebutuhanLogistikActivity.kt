package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.KebutuhanLogistikAdapter
import com.adit.poskoapp.adapters.onCLickAdapterKebutuhan
import com.adit.poskoapp.contracts.KebutuhanLogistikActivityContract
import com.adit.poskoapp.databinding.ActivityKebutuhanLogistikBinding
import com.adit.poskoapp.models.KebutuhanLogistik
import com.adit.poskoapp.presenters.KebutuhanLogistikActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.activity_donatur.*

class KebutuhanLogistikActivity : AppCompatActivity(), KebutuhanLogistikActivityContract.KebutuhanLogistikActivityView {

    private lateinit var binding : ActivityKebutuhanLogistikBinding
    private var presenter : KebutuhanLogistikActivityContract.KebutuhanLogistikPresenter? = null
    private lateinit var kebutuhanAdapter : KebutuhanLogistikAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKebutuhanLogistikBinding.inflate(layoutInflater)
        presenter = KebutuhanLogistikActivityPresenter(this)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this@KebutuhanLogistikActivity, CreateOrUpdateKebutuhanActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }).also{
                finish()
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun attachKebutuhanLogistikRecycler(kebutuhan_logistik: List<KebutuhanLogistik>) {
        kebutuhanAdapter = KebutuhanLogistikAdapter(kebutuhan_logistik , this, object : onCLickAdapterKebutuhan{
            override fun deleteData(kebutuhan_logistik: KebutuhanLogistik) {
                deleteData(kebutuhan_logistik.id.toString())
            }

        })
        binding.rvKebutuhanLogistik.apply {
            layoutManager = LinearLayoutManager(this@KebutuhanLogistikActivity)
            adapter = kebutuhanAdapter
        }
    }

    override fun showLoading() {
        binding.loadingKebutuhan.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loadingKebutuhan.apply {
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
        presenter?.infoKebutuhanLogistik(token!!)
    }

    private fun deleteData(id : String){
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