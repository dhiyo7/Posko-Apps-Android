package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.Penyaluran
import com.adit.poskoapp.models.Posko
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PenyaluranActivityContract {
    interface PenyaluranActivityView {
        fun showToast(message : String)
        fun attachPenyaluranRecycler(penyaluran : List<Penyaluran>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface PenyaluranActivityPresenter {
        fun infoPenyaluran(token : String)
        fun delete(token:String, id: String,)
        fun destroy()
    }

    interface CreateOrUpdateView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun success();
    }

    interface CreateOrUpdateInteraction{
        fun create(token : String, nama_penerima: RequestBody, jenis_kebutuhan: RequestBody, jumlah: RequestBody, satuan: RequestBody,status: RequestBody, keterangan: RequestBody, alamat: RequestBody, tanggal : RequestBody, foto : MultipartBody.Part)
        fun update(token : String, id: String, nama_penerima: RequestBody, jenis_kebutuhan: RequestBody, jumlah: RequestBody, satuan: RequestBody,status: RequestBody, keterangan: RequestBody, alamat: RequestBody, tanggal : RequestBody, foto : MultipartBody.Part, method : RequestBody)
        fun updateTanpaFoto(token : String, id: String, nama_penerima: RequestBody, jenis_kebutuhan: RequestBody, jumlah: RequestBody, satuan: RequestBody,status: RequestBody, keterangan: RequestBody, alamat: RequestBody, tanggal : RequestBody, method : RequestBody)
        fun destroy()
    }
}