package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskoapp.contracts.PetugasActivityContract
import com.adit.poskoapp.databinding.ActivityCreateAndUpdatePetugasBinding
import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.CreateAndUpdatePetugasActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils

class CreateAndUpdatePetugasActivity : AppCompatActivity(), PetugasActivityContract.CreateOrUpdateView {

    private lateinit var binding : ActivityCreateAndUpdatePetugasBinding
    private var presenter : PetugasActivityContract.CreateOrUpdatePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAndUpdatePetugasBinding.inflate(layoutInflater)
        presenter = CreateAndUpdatePetugasActivityPresenter(this)
        setupSpinnerLevel()
        doSave()
        fill()
        setContentView(binding.root)
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPetugas() : Petugas? = intent.getParcelableExtra("PETUGAS")

    private fun fill(){
        if(!isNew()){
            binding.etNama.setText(getPetugas()?.nama)
            binding.etUsername.setText(getPetugas()?.username)
            if(getPetugas()?.level == "Petugas"){
                binding.level.setSelection(1)
            }else{
                binding.level.setSelection(0)
            }
            binding.etPassWord.setText(getPetugas()?.password)
            binding.etConfirmPass.setText(getPetugas()?.password)
        }

    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this)

            var nama = binding.etNama.text.toString()
            var username = binding.etUsername.text.toString()
            var password = binding.etPassWord.text.toString()
            var konfirmasi_password = binding.etConfirmPass.text.toString()
            var level = binding.level.selectedItem

            var objectPosko = binding.idPosko.selectedItem as Posko

            var id_posko = objectPosko.id

            if(isNew()){
                presenter?.create(token!!, nama, username, password, konfirmasi_password, level.toString(), id_posko.toString())
            }else{
                presenter?.update(token!!, getPetugas()?.id.toString(), nama, username, password, konfirmasi_password, level.toString(), id_posko.toString())
            }
        }
    }

    private fun setupSpinnerLevel() {
        val spinnerLevelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.level_array))
        binding.level.adapter = spinnerLevelAdapter
    }

    override fun attachToSpinner(posko: List<Posko>) {
        var spinnerAdapterPosko = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, posko)
        binding.idPosko.apply {
            adapter = spinnerAdapterPosko
        }
    }

    override fun setSelectionSpinner(posko: List<Posko>) {
        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getPetugas()?.id_posko){
                    binding.idPosko.setSelection(item)
                }
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingCreateOrUpdate.apply {
            isIndeterminate = true
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
        val intent = Intent(this@CreateAndUpdatePetugasActivity, PetugasActivity::class.java)
        startActivity(intent)
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