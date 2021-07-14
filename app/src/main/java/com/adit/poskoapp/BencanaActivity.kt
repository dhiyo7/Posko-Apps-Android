package com.adit.poskoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.BencanaAdapter
import com.adit.poskoapp.adapters.onClickAdapterBencana
import com.adit.poskoapp.contracts.BencanaActivityContract
import com.adit.poskoapp.databinding.ActivityBencanaBinding
import com.adit.poskoapp.models.Bencana
import com.adit.poskoapp.presenters.BencanaActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.content_bencana.*

class BencanaActivity : AppCompatActivity(), BencanaActivityContract.View {

    private var presenter = BencanaActivityPresenter(this)


    private lateinit var binding: ActivityBencanaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = BencanaActivityPresenter(this)


        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Daftar Bencana"
        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this@BencanaActivity, CreateOrUpdateBencanaActivity::class.java).apply {
                putExtra("IS_NEW", true)
            })
        }
    }

    private fun delete(id: String){
        val token = PoskoUtils.getToken(this)
        presenter?.delete(token!!, id)
        startActivity(Intent(this@BencanaActivity, BencanaActivity::class.java).also{
            finish()
        })
    }

    private fun getData () {
        presenter?.allBencana()
    }

    override fun attachToRecycle(bencana: List<Bencana>) {
        rvBencana.apply {
            layoutManager = LinearLayoutManager(this@BencanaActivity)
            adapter = BencanaAdapter(bencana, this@BencanaActivity,object : onClickAdapterBencana{
                override fun deleteData(data: Bencana) {
                    delete(data!!.id.toString())
                }

            })
        }
    }

    override fun toast(message: String?) {
        Toast.makeText(this@BencanaActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun isLoading(state: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun notConnect(message: String?) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}