package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.KebutuhanLogistik
import com.adit.poskoapp.models.Posko

interface KebutuhanLogistikActivityContract {
    interface KebutuhanLogistikActivityView{
        fun showToast(message : String)
        fun attachKebutuhanLogistikRecycler(kebutuhan_logistik : List<KebutuhanLogistik>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface KebutuhanLogistikPresenter{
        fun infoKebutuhanLogistik(token : String)
        fun delete(token : String, id: String)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun success()
        fun attachToSpinner(posko: List<Posko>)
        fun setSelectionSpinner(posko: List<Posko>)
    }

    interface CreateOrUpdateInteraction{
        fun create(token: String, id_posko: String, jenis_kebutuhan : String, keterangan : String, jumlah: String, status: String, tanggal : String)
        fun update(token: String, id: String, id_posko: String, jenis_kebutuhan : String, keterangan : String, jumlah: String, status: String, tanggal : String)
        fun getPosko()
        fun destroy()
    }


}