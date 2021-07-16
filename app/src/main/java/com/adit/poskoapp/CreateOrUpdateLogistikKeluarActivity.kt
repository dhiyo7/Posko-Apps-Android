package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskoapp.contracts.LogistikKeluarActivityContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdateLogistikKeluarBinding
import com.adit.poskoapp.models.LogistikKeluar
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.CreateOrUpdateLogistikKeluarActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils

class CreateOrUpdateLogistikKeluarActivity : AppCompatActivity(), LogistikKeluarActivityContract.CreateOrUpdateView {

    private lateinit var binding : ActivityCreateOrUpdateLogistikKeluarBinding
    private var presenter : LogistikKeluarActivityContract.CreateOrUpdateInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrUpdateLogistikKeluarBinding.inflate(layoutInflater)
        presenter = CreateOrUpdateLogistikKeluarActivityPresenter(this)
        setContentView(binding.root)

        setupSpinner()
        fill()
        doSave()
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@CreateOrUpdateLogistikKeluarActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingCreateOrUpdate.isIndeterminate = true
    }

    override fun hideLoading() {
        binding.loadingCreateOrUpdate.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun attachToSpinner(posko: List<Posko>) {
        var spinnerAdapterPosko = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, posko)
        binding.spinnerPosko.apply {
            adapter = spinnerAdapterPosko
        }

        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getLogistikKeluar()?.id_posko_penerima?.toInt()){
                    binding.spinnerPosko.setSelection(item)
                }
            }
        }
    }

    override fun success() {
        val intent = Intent(this@CreateOrUpdateLogistikKeluarActivity, LogistikKeluarActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    private fun setupSpinner(){
        val spinnerJenisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            R.array.jenis_kebutuhan_array
        ))
        binding.spinnerJenis.adapter = spinnerJenisAdapter

        val spinnerStatusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            R.array.status_proses_terima
        ))
        binding.spinnerStatus.adapter = spinnerStatusAdapter

        val spinnerSatuanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            R.array.satuan_array
        ))
        binding.spinnerSatuan.adapter = spinnerSatuanAdapter

        if(!isNew()){
            val selectedJenis = spinnerJenisAdapter.getPosition(getLogistikKeluar()?.jenis_kebutuhan)
            binding.spinnerJenis.setSelection(selectedJenis)

            val selectedStatus = spinnerStatusAdapter.getPosition(getLogistikKeluar()?.status)
            binding.spinnerStatus.setSelection(selectedStatus)

            val selectedSatuan = spinnerSatuanAdapter.getPosition(getLogistikKeluar()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)
    private fun getLogistikKeluar() : LogistikKeluar? = intent.getParcelableExtra("LOGISTIK_KELUAR")

    private fun fill(){
        if(!isNew()){
            binding.etKeterangan.setText(getLogistikKeluar()?.keterangan)
            binding.etJumlah.setText(getLogistikKeluar()?.jumlah)
            binding.etDate.setText(getLogistikKeluar()?.tanggal)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this)
            val jenis_kebutuhan = binding.spinnerJenis.selectedItem.toString()
            val keterangan = binding.etKeterangan.text.toString()
            val jumlah = binding.etJumlah.text.toString()

            val selectedPosko = binding.spinnerPosko.selectedItem as Posko
            val id_posko_penerima = selectedPosko.id

            val status = binding.spinnerStatus.selectedItem.toString()
            val satuan = binding.spinnerSatuan.selectedItem.toString()
            val tanggal = binding.etDate.text.toString()

            if(isNew()){
                presenter?.create(token!!, jenis_kebutuhan, keterangan, jumlah, id_posko_penerima.toString(), status, tanggal, satuan)
            }else{
                presenter?.update(token!!, getLogistikKeluar()!!.id.toString(), jenis_kebutuhan, keterangan, jumlah, id_posko_penerima.toString(), status, tanggal, satuan)
            }
        }
    }

    private fun getData(){
        presenter?.getPosko()
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