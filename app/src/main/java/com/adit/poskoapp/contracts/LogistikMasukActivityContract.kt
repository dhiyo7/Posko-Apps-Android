package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.LogistikMasuk
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.models.Posko
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody

interface LogistikMasukActivityContract {
    interface LogistikMasukActivityView{
        fun showToast(message : String)
        fun attachLogistikMasukRecycler(logistik_masuk : List<LogistikMasuk>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface LogistikMasukPresenter{
        fun getLogistikMasuk(token: String)
        fun delete(token: String, id: Int)
        fun destroy()
    }

    interface CreateOrUpdateView{
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun attachToSpinner(posko: List<Posko>)
        fun success()
    }

    interface CreateOrUpdateInteraction{
        fun create(token: String, jenis_kebutuhan: RequestBody, keterangan : RequestBody, jumlah : RequestBody, pengirim : RequestBody, tanggal: RequestBody, status: RequestBody, satuan : RequestBody, foto : MultipartBody.Part)
        fun update(token: String, id: Int, jenis_kebutuhan: RequestBody, keterangan : RequestBody, jumlah : RequestBody, pengirim : RequestBody, tanggal: RequestBody, status: RequestBody, satuan : RequestBody, foto : MultipartBody.Part, method: RequestBody)
        fun updateTanpaFoto(token: String, id: Int, jenis_kebutuhan: RequestBody, keterangan : RequestBody, jumlah : RequestBody, pengirim : RequestBody, tanggal: RequestBody, status: RequestBody, satuan : RequestBody, method: RequestBody)
        fun getPosko()
        fun destroy()
    }
}