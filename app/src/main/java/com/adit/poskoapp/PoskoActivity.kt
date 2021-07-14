package com.adit.poskoapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.PoskoAdapter
import com.adit.poskoapp.contracts.PoskoActivityContract
import com.adit.poskoapp.databinding.ActivityPoskoBinding
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.PoskoActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.content_posko.*

class PoskoActivity : AppCompatActivity() , PoskoActivityContract.View{

    private var presenter = PoskoActivityPresenter(this)

    private lateinit var binding: ActivityPoskoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPoskoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = PoskoActivityPresenter(this)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = "Daftar Posko"
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        hideButtonWhenNotAuth()
    }

    private fun hideButtonWhenNotAuth(){
        val token = PoskoUtils.getToken(this)
        if(token == null || token.equals("UNDEFINED")){
            binding.fab.visibility = View.GONE
        }
    }

    private fun getData () {
        presenter?.allPosko()
    }

    override fun attachToRecycle(posko: List<Posko>) {
        rvPosko.apply {
            layoutManager = LinearLayoutManager(this@PoskoActivity)
            adapter = PoskoAdapter(posko, this@PoskoActivity)
        }
    }

    override fun toast(message: String?) {
        Toast.makeText(this@PoskoActivity, message, Toast.LENGTH_LONG).show()
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