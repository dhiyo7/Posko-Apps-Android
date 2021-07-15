package com.adit.poskoapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskoapp.contracts.DonaturActivityContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdateDonaturBinding
import com.adit.poskoapp.models.Donatur
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.CreateOrUpdateDonaturActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils

class CreateOrUpdateDonaturActivity : AppCompatActivity(), DonaturActivityContract.CreateOrUpdateView {

    private lateinit var binding : ActivityCreateOrUpdateDonaturBinding
    private var presenter : DonaturActivityContract.CreateOrUpdateInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrUpdateDonaturBinding.inflate(layoutInflater)
        presenter = CreateOrUpdateDonaturActivityPresenter(this)
        setContentView(binding.root)
        doSave()
        fill()
        setupSpinner()
    }

    override fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingCreateOrUpdate.apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loadingCreateOrUpdate.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun success() {
        val intent = Intent(this@CreateOrUpdateDonaturActivity, DonaturActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getDonatur() : Donatur? = intent.getParcelableExtra("DONATUR")

    private fun fill(){
        if(!isNew()){
            binding.etNama.setText(getDonatur()?.nama)
            binding.etPoskoPenerima.setText(getDonatur()?.posko_penerima)
            binding.etKeterangan.setText(getDonatur()?.keterangan)
            binding.etAlamat.setText(getDonatur()?.alamat)
            binding.etDate.setText(getDonatur()?.tanggal)
        }
    }

    private fun setupSpinner(){
        val spinnerJenisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            com.adit.poskoapp.R.array.jenis_kebutuhan_array
        ))
        binding.spinnerJenis.adapter = spinnerJenisAdapter

        if(!isNew()){
            val selectedJenisKebutuhan = spinnerJenisAdapter.getPosition(getDonatur()?.jenis_kebutuhan)
            binding.spinnerJenis.setSelection(selectedJenisKebutuhan)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this)
            var nama = binding.etNama.text.toString()
            var jenis = binding.spinnerJenis.selectedItem.toString()
            var posko_penerima = binding.etPoskoPenerima.text.toString()
            var keterangan = binding.etKeterangan.text.toString()
            var alamat = binding.etAlamat.text.toString()
            var date = binding.etDate.text.toString()

            if (isNew()){
                presenter?.create(token!!, nama, posko_penerima, jenis, keterangan,alamat, date)
            }else{
                presenter?.update(token!!, getDonatur()?.id.toString(), nama, posko_penerima, jenis, keterangan,alamat, date)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }


}