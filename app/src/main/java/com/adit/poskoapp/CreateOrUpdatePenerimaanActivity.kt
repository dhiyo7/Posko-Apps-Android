package com.adit.poskoapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskoapp.contracts.PenerimaanLogisitkContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdatePenerimaanBinding
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.CreateOrUpdatePenerimaanActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.activity_create_or_update_penerimaan.*

class CreateOrUpdatePenerimaanActivity : AppCompatActivity(), PenerimaanLogisitkContract.CreateOrUpdateView {

    private lateinit var binding: ActivityCreateOrUpdatePenerimaanBinding
    private var presenter : PenerimaanLogisitkContract.CreateOrUpdateInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CreateOrUpdatePenerimaanActivityPresenter(this)
        binding = ActivityCreateOrUpdatePenerimaanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doSave()
        setupSpinnerLevel()
        fill()
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@CreateOrUpdatePenerimaanActivity, message, Toast.LENGTH_LONG).show()
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
        val intent = Intent(this@CreateOrUpdatePenerimaanActivity, PenerimaanLogistikActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    override fun attachToSpinner(posko: List<Posko>) {
        var spinnerAdapterPosko = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, posko)
        binding.poskopenerima.apply {
            adapter = spinnerAdapterPosko
        }
    }

    private fun fill(){
        if(!isNew()){
            binding.etDate.setText(getPenerimaan()?.tanggal)
            binding.etJenis.setText(getPenerimaan()?.jenis_kebutuhan)
            binding.etKeterangan.setText(getPenerimaan()?.keterangan)
            binding.etJumlah.setText(getPenerimaan()?.jumlah.toString())

            val satuanSelected = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(com.adit.poskoapp.R.array.satuan_array))
            var positionSatuan = satuanSelected.getPosition(getPenerimaan()?.satuan)
            binding.satuan.apply {
                adapter = satuanSelected
                setSelection(positionSatuan)

            }
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPenerimaan() : PenerimaanLogistik? = intent.getParcelableExtra("PENERIMAAN")

    override fun setSelectionSpinner(posko: List<Posko>) {
        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getPenerimaan()?.id_posko){
                    binding.poskopenerima.setSelection(item)
                }
            }
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            showLoading()
            val token = PoskoUtils.getToken(this)
            var objectPosko = binding.poskopenerima.selectedItem as Posko
            var id_posko = objectPosko.id

            var jenis_kebutuhan = binding.etJenis.text.toString()
            var keterangan = binding.etKeterangan.text.toString()
            var jumlah = binding.etJumlah.text.toString()
            var status = "Proses"
            var satuan = binding.satuan.selectedItem
            var tanggal = binding.etDate.text.toString()

            if(isNew()){
                presenter?.create(token!!,id_posko!!.toString(), jenis_kebutuhan, keterangan, jumlah, satuan.toString(), status, tanggal)
            }else{
                presenter?.update(token!!, getPenerimaan()?.id.toString(), id_posko!!.toString(), jenis_kebutuhan, keterangan, jumlah, satuan.toString(), status, tanggal)
            }
        }

    }

    private fun setupSpinnerLevel() {
        val spinnerLevelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            com.adit.poskoapp.R.array.satuan_array))
        binding.satuan.adapter = spinnerLevelAdapter
    }

    private fun getPosko(){
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