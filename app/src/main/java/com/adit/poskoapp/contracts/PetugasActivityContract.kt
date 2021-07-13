package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.models.Posko

interface PetugasActivityContract {
    interface PetugasActivityView {
        fun showToast(message : String)
        fun attachPetugasRecycler(petugas : List<Petugas>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface PetugasActivityPresenter {
        fun infoPetugas(token : String)
        fun delete(token:String, id: String,)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun attachToSpinner(posko: List<Posko>)
        fun setSelectionSpinner(posko: List<Posko>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun success();
    }

    interface CreateOrUpdatePresenter{
        fun create(token:String, nama: String, username: String, password: String, konfirmasi_password: String, level: String, id_posko : String)
        fun update(token:String, id: String, nama: String, username: String, password: String, konfirmasi_password: String, level: String, id_posko : String)
        fun getPosko()
        fun destroy()
    }
}