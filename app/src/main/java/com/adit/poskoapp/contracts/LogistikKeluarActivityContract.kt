package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.LogistikKeluar
import com.adit.poskoapp.models.Posko
import retrofit2.http.Field

interface LogistikKeluarActivityContract {
    interface LogistikKeluarActivityView{
        fun showToast(message : String)
        fun attachLogistikKeluarRecycler(logistik_keluar : List<LogistikKeluar>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface LogistikKeluarPresenter{
        fun getLogistikKeluar(token: String)
        fun delete(token: String, id: String)
        fun destroy()
    }

    interface CreateOrUpdateView{
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun attachToSpinner(posko : List<Posko>)
        fun success()
    }

    interface CreateOrUpdateInteraction{
        fun create(token: String, jenis_kebutuhan: String,keterangan: String,jumlah: String, id_posko_penerima: String,status: String,tanggal: String,satuan: String)
        fun update(token: String, id: String, jenis_kebutuhan: String,keterangan: String,jumlah: String, id_posko_penerima: String,status: String,tanggal: String,satuan: String)
        fun getPosko()
        fun destroy()
    }
}