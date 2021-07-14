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
        fun attachToSpinner(posko: List<Posko>)
        fun setSelectionSpinner(posko: List<Posko>)
    }

    interface CreateOrUpdateInteraction{
        fun create(token: String, nama: String, id_posko: String, jenis_kebutuhan : String, keterangan : String, alamat: String, tanggal : String)
        fun update(token: String, id: String, nama: String, id_posko: String, jenis_kebutuhan : String, keterangan : String, alamat: String, tanggal : String)
        fun getPosko()
        fun destroy()
    }
}
