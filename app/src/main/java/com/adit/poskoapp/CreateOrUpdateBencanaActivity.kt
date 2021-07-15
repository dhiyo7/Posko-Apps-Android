package com.adit.poskoapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import coil.api.load
import com.adit.poskoapp.contracts.BencanaActivityContract
import com.adit.poskoapp.databinding.ActivityCreateOrUpdateBencanaBinding
import com.adit.poskoapp.models.Bencana
import com.adit.poskoapp.presenters.CreateOrUpdateBencanaActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreateOrUpdateBencanaActivity : AppCompatActivity(), BencanaActivityContract.ViewCreate {

    private lateinit var binding : ActivityCreateOrUpdateBencanaBinding
    private var presenter : BencanaActivityContract.InteractionPost? = null
    private var choosedImage: Image? = null
    private var image : MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrUpdateBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = CreateOrUpdateBencanaActivityPresenter(this)
        binding.imageView.setOnClickListener {
            chooseImage()
        }
        fill()
        doSave()
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
        AlertDialog.Builder(this@CreateOrUpdateBencanaActivity).apply {
            setMessage(message)
            setPositiveButton("OK"){ d, _ ->
                d.cancel()
            }
        }.show()

        hideLoading()
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getBencana() : Bencana? = intent.getParcelableExtra("BENCANA")

    private fun fill(){
        if(!isNew()){
            binding.etNama.setText(getBencana()!!.nama)
            binding.imageView.load(getBencana()?.foto)
            binding.etDetail.setText(getBencana()!!.detail)
            binding.etDate.setText(getBencana()!!.date)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            showLoading()
            val token = PoskoUtils.getToken(this)
            val namaBencana : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etNama.text.toString()
            )

            val detailBencana : RequestBody = RequestBody.create(
                MultipartBody.FORM, binding.etDetail.text.toString()
            )

            val dateBencana : RequestBody = RequestBody.create(
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
                if(choosedImage == null){
                    showAlertDialog("Silahkan pilih foto")
                    return@setOnClickListener
                }
                presenter?.create(token!!, namaBencana, image!!, detailBencana, dateBencana)
            }else{
                if(choosedImage == null){
                    presenter?.updateTanpaFoto(token!!, getBencana()!!.id.toString(), namaBencana,detailBencana, dateBencana, methodBody)
                }else{
                    presenter?.update(token!!, getBencana()!!.id.toString(), namaBencana, image!!, detailBencana, dateBencana, methodBody)
                }
            }
        }
    }

    override fun success() {
        val intent = Intent(this@CreateOrUpdateBencanaActivity, MainActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@CreateOrUpdateBencanaActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingBencana.apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loadingBencana.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }
}