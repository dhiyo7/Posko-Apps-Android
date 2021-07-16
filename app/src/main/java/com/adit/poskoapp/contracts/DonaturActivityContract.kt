package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.Donatur
import com.adit.poskoapp.models.Posko

interface DonaturActivityContract {
    interface DonaturActivityView {
        fun showToast(message : String)
        fun attachDonaturRecycler(data_donatur : List<Donatur>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface DonaturActivityPresenter {
        fun infoDonatur()
        fun delete(token: String, id : String)
        fun destroy()
    }

    interface CreateOrUpdateView{
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface CreateOrUpdateInteraction{
        fun create(token: String, nama: String, posko_penerima: String, jenis_kebutuhan : String, keterangan : String, alamat: String, tanggal : String)
        fun update(token: String, id: String, nama: String, posko_penerima: String, jenis_kebutuhan : String, keterangan : String, alamat: String, tanggal : String)
        fun destroy()
    }
}
