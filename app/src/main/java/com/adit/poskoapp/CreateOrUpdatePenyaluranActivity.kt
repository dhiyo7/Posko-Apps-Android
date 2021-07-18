package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import coil.api.load
import com.adit.poskoapp.contracts.PenyaluranActivityContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdatePenyaluranBinding
import com.adit.poskoapp.models.Penyaluran
import com.adit.poskoapp.presenters.CreateOrUpdatePenyaluranActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreateOrUpdatePenyaluranActivity : AppCompatActivity(), PenyaluranActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityCreateOrUpdatePenyaluranBinding
    private var presenter : PenyaluranActivityContract.CreateOrUpdateInteraction? = null
    private var choosedImage: Image? = null
    private var image : MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrUpdatePenyaluranBinding.inflate(layoutInflater)
        presenter = CreateOrUpdatePenyaluranActivityPresenter(this)
        setupSpinner()
        fill()
        doSave()

        binding.imageView.setOnClickListener {
            chooseImage()
        }
        setContentView(binding.root)
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

    override fun showToast(message: String) {
        Toast.makeText(this@CreateOrUpdatePenyaluranActivity, message, Toast.LENGTH_LONG).show()
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
        val intent = Intent(this@CreateOrUpdatePenyaluranActivity, PenyaluranActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    private fun setupSpinner(){
        val spinnerJenisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            R.array.jenis_kebutuhan_array
        ))
        binding.spinnerJenis.adapter = spinnerJenisAdapter

        val spinnerSatuanAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            R.array.satuan_array
        ))
        binding.satuan.adapter = spinnerSatuanAdapter

        val spinnerStatusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            R.array.status_proses_terima
        ))
        binding.spinnerStatus.adapter = spinnerStatusAdapter

        if(!isNew()){
            val selectedJenis = spinnerJenisAdapter.getPosition(getPenyaluran()?.jenis_kebutuhan)
            val selectedStatus = spinnerStatusAdapter.getPosition(getPenyaluran()?.status)
            val selectedSatuan = spinnerSatuanAdapter.getPosition(getPenyaluran()?.satuan)

            binding.spinnerJenis.setSelection(selectedJenis)
            binding.spinnerStatus.setSelection(selectedStatus)
            binding.satuan.setSelection(selectedSatuan)
        }
    }
    
    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPenyaluran() : Penyaluran? = intent.getParcelableExtra("PENYALURAN")

    private fun fill(){
        if(!isNew()){
            binding.etPenerima.setText(getPenyaluran()?.nama_penerima)
            binding.etJumlah.setText(getPenyaluran()?.jumlah)
            binding.etAlamat.setText(getPenyaluran()?.alamat)
            binding.etKeterangan.setText(getPenyaluran()?.keterangan)
            binding.etDate.setText(getPenyaluran()?.tanggal)
            binding.imageView.load(getPenyaluran()?.foto)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this);
            val nama_penerima = RequestBody.create(
                MultipartBody.FORM, binding.etPenerima.text.toString()
            )

            val jenis_kebutuhan = RequestBody.create(
                MultipartBody.FORM, binding.spinnerJenis.selectedItem.toString()
            )

            val jumlah = RequestBody.create(
                MultipartBody.FORM, binding.etJumlah.text.toString()
            )

            val satuan = RequestBody.create(
                MultipartBody.FORM, binding.satuan.selectedItem.toString()
            )

            val status = RequestBody.create(
                MultipartBody.FORM, binding.spinnerStatus.selectedItem.toString()
            )

            val alamat = RequestBody.create(
                MultipartBody.FORM, binding.etAlamat.text.toString()
            )

            val keterangan = RequestBody.create(
                MultipartBody.FORM, binding.etKeterangan.text.toString()
            )

            val tanggal = RequestBody.create(
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

            val methodBody : RequestBody = RequestBody.create(
                MultipartBody.FORM, "PUT"
            )

            if(isNew()){
                presenter?.create(token!!, nama_penerima, jenis_kebutuhan, jumlah, satuan, status, keterangan, alamat, tanggal, image!!)
            }else{
                if(choosedImage == null){
                    presenter?.updateTanpaFoto(token!!, getPenyaluran()?.id.toString(), nama_penerima, jenis_kebutuhan, jumlah, satuan, status, keterangan, alamat, tanggal, methodBody)
                }else{
                    presenter?.update(token!!, getPenyaluran()?.id.toString(), nama_penerima, jenis_kebutuhan, jumlah, satuan, status, keterangan, alamat, tanggal, image!!, methodBody)
                }
            }
        }

    }
}