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

    override fun attachToSpinner(posko: List<Posko>) {
        var spinnerAdapterPosko = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, posko)
        binding.poskopenerima.apply {
            adapter = spinnerAdapterPosko
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getDonatur() : Donatur? = intent.getParcelableExtra("DONATUR")

    override fun setSelectionSpinner(posko: List<Posko>) {
        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getDonatur()?.id_posko!!.toInt()){
                    binding.poskopenerima.setSelection(item)
                }
            }
        }
    }

    private fun fill(){
        if(!isNew()){
            binding.etAlamat.setText(getDonatur()?.alamat)
            binding.etDate.setText(getDonatur()?.tanggal)
            binding.etJenis.setText(getDonatur()?.jenis_kebutuhan)
            binding.etKeterangan.setText(getDonatur()?.keterangan)
            binding.etNama.setText(getDonatur()?.nama)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this)
            var nama = binding.etNama.text.toString()
            var objectPosko = binding.poskopenerima.selectedItem as Posko
            var id_posko = objectPosko.id
            var jenis = binding.etJenis.text.toString()
            var keterangan = binding.etKeterangan.text.toString()
            var alamat = binding.etAlamat.text.toString()
            var date = binding.etDate.text.toString()

            if (isNew()){
                presenter?.create(token!!, nama, id_posko.toString(), jenis, keterangan, alamat, date)
            }else{
                presenter?.update(token!!, getDonatur()?.id.toString(), nama, id_posko.toString(), jenis, keterangan, alamat, date)
            }

        }
    }

    private fun getPosko() {
        presenter?.getPosko()
    }

    override fun onResume() {
        super.onResume()
        getPosko()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }


}