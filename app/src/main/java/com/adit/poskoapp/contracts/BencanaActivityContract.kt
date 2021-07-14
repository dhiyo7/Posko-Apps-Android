package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.Bencana
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface BencanaActivityContract {
    interface View {
        fun attachToRecycle(bencana: List<Bencana>)
        fun toast(message: String?)
        fun isLoading(state: Boolean?)
        fun notConnect(message: String?)
    }

    interface ViewCreate {
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface Interaction {
        fun allBencana()
        fun delete(token:String, id: String)
        fun destroy()
    }

    interface InteractionPost {
        fun create(token: String, name: RequestBody, foto: MultipartBody.Part, detail : RequestBody, date: RequestBody)
        fun update(token: String, id: String, name: RequestBody, foto: MultipartBody.Part, detail : RequestBody, date: RequestBody, method : RequestBody)
        fun updateTanpaFoto(token: String, id: String, name: RequestBody, detail : RequestBody, date: RequestBody, method : RequestBody)
        fun destroy()
    }

}