package com.adit.poskoapp

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.BencanaAdapter
import com.adit.poskoapp.contracts.BencanaActivityContract
import com.adit.poskoapp.databinding.ActivityBencanaBinding
import com.adit.poskoapp.models.Bencana
import com.adit.poskoapp.presenters.BencanaActivityPresenter
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
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }


    private fun getData () {
        presenter?.allBencana()
    }

    override fun attachToRecycle(bencana: List<Bencana>) {
        rvBencana.apply {
            layoutManager = LinearLayoutManager(this@BencanaActivity)
            adapter = BencanaAdapter(bencana, this@BencanaActivity)
        }    }

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