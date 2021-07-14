package com.adit.poskoapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskoapp.contracts.KebutuhanLogistikActivityContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdateKebutuhanBinding
import com.adit.poskoapp.databinding.ActivityKebutuhanLogistikBinding
import com.adit.poskoapp.models.KebutuhanLogistik
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.CreateOrUpdateKebutuhanActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils

class CreateOrUpdateKebutuhanActivity : AppCompatActivity(), KebutuhanLogistikActivityContract.CreateOrUpdateView {

    private lateinit var binding : ActivityCreateOrUpdateKebutuhanBinding
    private var presenter : KebutuhanLogistikActivityContract.CreateOrUpdateInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrUpdateKebutuhanBinding.inflate(layoutInflater)
        presenter = CreateOrUpdateKebutuhanActivityPresenter(this)
        setContentView(binding.root)
        fill()
        doSave()
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
        val intent = Intent(this@CreateOrUpdateKebutuhanActivity, KebutuhanLogistikActivity::class.java).also{
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

    override fun setSelectionSpinner(posko: List<Posko>) {
        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getKebutuhan()?.id_posko){
                    binding.poskopenerima.setSelection(item)
                }
            }
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getKebutuhan() : KebutuhanLogistik? = intent.getParcelableExtra("KEBUTUHAN")

    private fun fill(){
        if(!isNew()){
            binding.etDate.setText(getKebutuhan()?.tanggal)
            binding.etJenis.setText(getKebutuhan()?.jenis_kebutuhan)
            binding.etKeterangan.setText(getKebutuhan()?.keterangan)
            binding.etJumlah.setText(getKebutuhan()?.jumlah.toString())
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this)
            var objectPosko = binding.poskopenerima.selectedItem as Posko
            var id_posko = objectPosko.id

            var jenis_kebutuhan = binding.etJenis.text.toString()
            var keterangan = binding.etKeterangan.text.toString()
            var jumlah = binding.etJumlah.text.toString()
            var status = "Belum Terpenuhi"
            var tanggal = binding.etDate.text.toString()

            if(isNew()){
                presenter?.create(token!!,id_posko!!.toString(), jenis_kebutuhan, keterangan, jumlah, status, tanggal)
            }else{
                presenter?.update(token!!, getKebutuhan()?.id.toString(), id_posko!!.toString(), jenis_kebutuhan, keterangan, jumlah, status, tanggal)
            }
        }

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