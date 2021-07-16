package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.Posko

interface PoskoActivityContract {
    interface View {
        fun attachToRecycle(posko: List<Posko>)
        fun toast(message: String?)
        fun isLoading(state: Boolean?)
        fun notConnect(message: String?)
    }

    interface Interaction {
        fun allPosko()
        fun delete(token : String, id: String)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface CreateOrUpdateInteraction {
        fun create(token: String, nama : String, jumlah_pengungsi: String, kontak_hp: String, lokasi: String, longitude : String, latitude: String)
        fun update(token: String, id: String, nama : String, jumlah_pengungsi: String, kontak_hp: String, lokasi: String, longitude : String, latitude: String)
        fun destroy()
    }
}