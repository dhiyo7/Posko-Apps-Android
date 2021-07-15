package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.models.Posko
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PenerimaanLogisitkContract {
    interface PenerimaanLogistikActivityView{
        fun showToast(message : String)
        fun attachPenerimaanLogistikRecycler(data_penerimaan_logistik : List<PenerimaanLogistik>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface PenerimmaanLogistikPresenter{
        fun infoPenerimaanLogistik()
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
        fun create(token: String, id_posko: String, jenis_kebutuhan : String, keterangan : String, jumlah: String, satuan: String, status: String, tanggal : String)
        fun update(token: String, id: String, id_posko: String, jenis_kebutuhan : String, keterangan : String, jumlah: String, satuan: String, status: String, tanggal : String)
        fun getPosko()
        fun destroy()
    }
}