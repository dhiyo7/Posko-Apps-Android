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
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, posko)
        binding.spinnerPengirim.apply {
            adapter = spinnerAdapter
        }

        binding.spinnerPosko.apply {
            adapter = spinnerAdapter
        }

        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getLogistikMasuk()?.id_posko!!.toInt()){
                    binding.spinnerPosko.setSelection(item)
                }

                if(posko[item].nama == getLogistikMasuk()?.pengirim){
                    binding.spinnerPengirim.setSelection(item)
                }
            }
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
            binding.etJenis.setText(getLogistikMasuk()?.jenis_kebutuhan)
            binding.etDate.setText(getLogistikMasuk()?.tanggal)
            binding.imageView.load(getLogistikMasuk()?.foto)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = PoskoUtils.getToken(this)

            val jenis : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etJenis.text.toString()
            )


            val keterangan : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etKeterangan.text.toString()
            )


            val jumlah : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etJumlah.text.toString()
            )

            val tanggal : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etDate.text.toString()
            )

            val selectedPengirim = binding.spinnerPengirim.selectedItem as Posko
            val pengirim : RequestBody = RequestBody.create(
                MultipartBody.FORM, selectedPengirim.nama.toString()
            )

            val selectedPosko = binding.spinnerPosko.selectedItem as Posko
            val id_posko  : RequestBody = RequestBody.create(
                MultipartBody.FORM, selectedPosko.id.toString()
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
                showLoading()
                if(choosedImage == null){
                    showAlertDialog("Silahkan pilih foto")
                    return@setOnClickListener
                }
                presenter?.create(token!!, jenis, keterangan, jumlah, pengirim, id_posko, image!!, tanggal )
            }else{
                if(choosedImage == null){
                    presenter?.updateTanpaFoto(token!!, getLogistikMasuk()!!.id!!.toInt(), jenis, keterangan, jumlah, pengirim, id_posko, tanggal,methodBody)
                }else{
                    presenter?.update(token!!, getLogistikMasuk()!!.id!!.toInt(), jenis, keterangan, jumlah, pengirim, id_posko, image!!, tanggal, methodBody)
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