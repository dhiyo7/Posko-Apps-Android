package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.adapters.PetugasAdapter
import com.adit.poskoapp.adapters.onClickAdapter
import com.adit.poskoapp.contracts.PetugasActivityContract
import com.adit.poskoapp.databinding.ActivityPetugasBinding
import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.presenters.PetugasActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils

class PetugasActivity : AppCompatActivity(), PetugasActivityContract.PetugasActivityView {

    private lateinit var binding : ActivityPetugasBinding
    private var presenter : PetugasActivityContract.PetugasActivityPresenter? = null
    private lateinit var adapterPetugas : PetugasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetugasBinding.inflate(layoutInflater)
        presenter = PetugasActivityPresenter(this)

        binding.fab.setOnClickListener {
            startActivity(Intent(this@PetugasActivity, CreateAndUpdatePetugasActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }).also{
                finish()
            }
        }

        setContentView(binding.root)
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PetugasActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachPetugasRecycler(petugas: List<Petugas>) {
        binding.rvPetugas.apply {
            adapterPetugas = PetugasAdapter(petugas, this@PetugasActivity, object: onClickAdapter{
                override fun edit(petugas: Petugas) {
                    val intent = Intent(this@PetugasActivity, CreateAndUpdatePetugasActivity::class.java).apply {
                        putExtra("IS_NEW", false)
                        putExtra("PETUGAS", petugas)
                    }
                    startActivity(intent).also{
                        finish()
                    }
                }

                override fun showDialog(petugas: Petugas) {
                    delete(petugas!!.id.toString())
                }

            })
            layoutManager = LinearLayoutManager(this@PetugasActivity)
            adapter = adapterPetugas
        }
    }

    override fun showLoading() {
        binding.loadingPetugas.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loadingPetugas.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun showDataEmpty() {
        binding.emptyDataPetugas.apply {
            visibility = View.VISIBLE
        }
    }

    override fun hideDataEmpty() {
        binding.emptyDataPetugas.apply {
            visibility = View.GONE
        }
    }

    private fun getPetugas() {
        val token = PoskoUtils.getToken(this)
        presenter?.infoPetugas(token!!)
    }

    private fun delete(id: String){
        val token = PoskoUtils.getToken(this)
        presenter?.delete(token!!, id)
        startActivity(Intent(this@PetugasActivity, PetugasActivity::class.java).also{
            finish()
        })
    }

    override fun onResume() {
        super.onResume()
        getPetugas()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}