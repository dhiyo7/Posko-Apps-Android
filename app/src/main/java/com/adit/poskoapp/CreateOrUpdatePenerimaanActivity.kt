package com.adit.poskoapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import coil.api.load
import com.adit.poskoapp.contracts.PenerimaanLogisitkContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdatePenerimaanBinding
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.CreateOrUpdatePenerimaanActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import kotlinx.android.synthetic.main.activity_create_or_update_penerimaan.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreateOrUpdatePenerimaanActivity : AppCompatActivity(), PenerimaanLogisitkContract.CreateOrUpdateView {

    private lateinit var binding: ActivityCreateOrUpdatePenerimaanBinding
    private var presenter : PenerimaanLogisitkContract.CreateOrUpdateInteraction? = null
    private var choosedImage: Image? = null
    private var image : MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CreateOrUpdatePenerimaanActivityPresenter(this)
        binding = ActivityCreateOrUpdatePenerimaanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        doSave()
        setupSpinnerLevel()
        fill()

        binding.imageView.setOnClickListener {
            chooseImage()
        }
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@CreateOrUpdatePenerimaanActivity, message, Toast.LENGTH_LONG).show()
    }

    private val imagePickerLauncher = registerImagePicker {
        choosedImage = it[0]
        showImage()
    }

    private fun chooseImage(){
        val config = ImagePickerConfig{
            mode = ImagePickerMode.SINGLE
            isIncludeVideo = false
            isShowCamera = false
        }

        imagePickerLauncher.launch(config)
    }

    private fun showImage(){
        choosedImage?.let{
                image -> binding.imageView.load(image.uri)
        }
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
            binding.etPengirim.setText(getPenerimaan()?.pengirim)
            binding.etKeterangan.setText(getPenerimaan()?.keterangan)
            binding.etJumlah.setText(getPenerimaan()?.jumlah.toString())
            binding.etDate.setText(getPenerimaan()?.tanggal)
            binding.imageView.load(getPenerimaan()?.foto)
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

            val pengirim = RequestBody.create(
                MultipartBody.FORM, binding.etPengirim.text.toString()
            )

            var objectPosko = binding.poskopenerima.selectedItem as Posko

            var id_posko : RequestBody = RequestBody.create(
                MultipartBody.FORM, objectPosko.id.toString()
            )

            var jenis_kebutuhan :RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.spinnerJenis.selectedItem.toString()
            )

            var keterangan :RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etKeterangan.text.toString()
            )

            var jumlah : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etJumlah.text.toString()
            );
            var status = RequestBody.create(
                MultipartBody.FORM, binding.spinnerStatus.selectedItem.toString()
            )

            var satuan : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.satuan.selectedItem.toString()
            )
            var tanggal : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etDate.text.toString()
            )

            if(choosedImage != null){
                var originalFile = File(choosedImage?.path!!)

                var imagePart : RequestBody = RequestBody.create(
                    MediaType.parse("image/*"),
                    originalFile
                )

                image = MultipartBody.Part.createFormData(
                    "foto",
                    originalFile.name,
                    imagePart
                )
            }

            var method :RequestBody = RequestBody.create(
                MultipartBody.FORM, "PUT"
            )

            if(isNew()){
                if(choosedImage == null){
                    showAlertDialog("Silahkan pilih foto")
                    return@setOnClickListener
                }
                presenter?.create(token!!, pengirim, id_posko, jenis_kebutuhan, keterangan, jumlah, satuan, status, tanggal , image!!)
            }else{
                if(choosedImage == null){
                    presenter?.updateTanpaFoto(token!!, getPenerimaan()?.id!!.toInt(), pengirim, id_posko, jenis_kebutuhan, keterangan, jumlah, satuan, status, tanggal, method)
                }else{
                    presenter?.update(token!!, getPenerimaan()?.id!!.toInt(), pengirim, id_posko, jenis_kebutuhan, keterangan, jumlah, satuan, status, tanggal , image!!, method)
                }
            }
        }

    }

    private fun showAlertDialog(message: String){
        AlertDialog.Builder(this@CreateOrUpdatePenerimaanActivity).apply {
            setMessage(message)
            setPositiveButton("OK"){ d, _ ->
                d.cancel()
            }
        }.show()

        hideLoading()
    }

    private fun setupSpinnerLevel() {
        val spinnerLevelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            com.adit.poskoapp.R.array.satuan_array))

        binding.satuan.adapter = spinnerLevelAdapter

        val spinnerJenisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            com.adit.poskoapp.R.array.jenis_kebutuhan_array))
        binding.spinnerJenis.adapter = spinnerJenisAdapter


        val spinnerStatusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            com.adit.poskoapp.R.array.status_proses_terima))
        binding.spinnerStatus.adapter = spinnerStatusAdapter

        if(!isNew()){
            var jenisSelected = spinnerJenisAdapter.getPosition(getPenerimaan()?.jenis_kebutuhan)
            binding.spinnerJenis.setSelection(jenisSelected)

            var statusSelected = spinnerStatusAdapter.getPosition(getPenerimaan()?.status)
            binding.spinnerStatus.setSelection(statusSelected)

            var satuanSelected = spinnerLevelAdapter.getPosition(getPenerimaan()?.satuan)
            binding.satuan.setSelection(satuanSelected)
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