package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import coil.api.load
import com.adit.poskoapp.contracts.LogistikMasukActivityContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdateLogistikMasukBinding
import com.adit.poskoapp.models.LogistikMasuk
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.presenters.CreateOrUpdateLogistikMasukActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import kotlinx.android.synthetic.main.activity_create_or_update_logistik_masuk.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File

class CreateOrUpdateLogistikMasukActivity : AppCompatActivity(), LogistikMasukActivityContract.CreateOrUpdateView {

    private lateinit var binding : ActivityCreateOrUpdateLogistikMasukBinding
    private var presenter : LogistikMasukActivityContract.CreateOrUpdateInteraction? = null
    private var choosedImage: Image? = null
    private var image : MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrUpdateLogistikMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = CreateOrUpdateLogistikMasukActivityPresenter(this)

        binding.imageView.setOnClickListener {
            chooseImage()
        }
        fill()
        doSave()
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@CreateOrUpdateLogistikMasukActivity, message, Toast.LENGTH_LONG).show()
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
            val selectedJenis = spinnerJenisAdapter.getPosition(getLogistikMasuk()?.jenis_kebutuhan)
            binding.spinnerJenis.setSelection(selectedJenis)

            val selectedStatus = spinnerStatusAdapter.getPosition(getLogistikMasuk()?.status)
            binding.spinnerStatus.setSelection(selectedStatus)

            val selectedSatuan = spinnerSatuanAdapter.getPosition(getLogistikMasuk()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)
        }
    }

    override fun success() {
        val intent = Intent(this@CreateOrUpdateLogistikMasukActivity, LogistikMasukActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getLogistikMasuk() : LogistikMasuk? = intent.getParcelableExtra("LOGISTIK_MASUK")

    private fun fill(){
        if(!isNew()){
            binding.etJumlah.setText(getLogistikMasuk()?.jumlah)
            binding.etKeterangan.setText(getLogistikMasuk()?.keterangan)
            binding.etDate.setText(getLogistikMasuk()?.tanggal)
            binding.etPengirim.setText(getLogistikMasuk()?.pengirim)
            binding.imageView.load(getLogistikMasuk()?.foto)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this)

            val jenis_kebutuhan = RequestBody.create(
                MultipartBody.FORM, binding.spinnerJenis.selectedItem.toString()
            )

            val keterangan = RequestBody.create(
                MultipartBody.FORM, binding.etKeterangan.text.toString()
            )

            val jumlah = RequestBody.create(
                MultipartBody.FORM, binding.etJumlah.text.toString()
            )

            val pengirim = RequestBody.create(
                MultipartBody.FORM, binding.etPengirim.text.toString()
            )

            val tanggal = RequestBody.create(
                MultipartBody.FORM, binding.etDate.text.toString()
            )

            val status = RequestBody.create(
                MultipartBody.FORM, binding.spinnerStatus.selectedItem.toString()
            )

            val satuan = RequestBody.create(
                MultipartBody.FORM, binding.spinnerSatuan.selectedItem.toString()
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

            val method = RequestBody.create(
                MultipartBody.FORM, "PUT"
            )

            if(isNew()){
                presenter?.create(token!!, jenis_kebutuhan, keterangan, jumlah, pengirim, tanggal, status, satuan, image!!)
            }else{
                if(choosedImage == null){
                    presenter?.updateTanpaFoto(token!!, getLogistikMasuk()?.id!!.toInt(), jenis_kebutuhan, keterangan, jumlah, pengirim, tanggal, status, satuan, method)
                }else{
                    presenter?.update(token!!, getLogistikMasuk()?.id!!.toInt(), jenis_kebutuhan, keterangan, jumlah, pengirim, tanggal, status, satuan, image!!, method)
                }
            }
        }
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

    private fun showAlertDialog(message: String){
        AlertDialog.Builder(this@CreateOrUpdateLogistikMasukActivity).apply {
            setMessage(message)
            setPositiveButton("OK"){ d, _ ->
                d.cancel()
            }
        }.show()

        hideLoading()
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