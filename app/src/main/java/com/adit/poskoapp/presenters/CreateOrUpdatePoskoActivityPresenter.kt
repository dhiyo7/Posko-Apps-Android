package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.PoskoActivityContract
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdatePoskoActivityPresenter(v: PoskoActivityContract.CreateOrUpdateView?) : PoskoActivityContract.CreateOrUpdateInteraction {
    private var view : PoskoActivityContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()

    override fun create(
        token: String,
        nama: String,
        jumlah_pengungsi: String,
        kontak_hp: String,
        lokasi: String,
        longitude: String,
        latitude: String
    ) {
        val request = api.postPosko(token, nama, jumlah_pengungsi, kontak_hp, lokasi, latitude, longitude)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<Posko>>{
            override fun onResponse(
                call: Call<WrappedResponse<Posko>>,
                response: Response<WrappedResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast("Terjadi kesalahan")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast(response.message())
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Posko>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        nama: String,
        jumlah_pengungsi: String,
        kontak_hp: String,
        lokasi: String,
        longitude: String,
        latitude: String
    ) {
        val request  = api.editPosko(token, id.toInt(), nama, jumlah_pengungsi, kontak_hp, lokasi, latitude, longitude)
        request.enqueue(object : Callback<WrappedResponse<Posko>>{
            override fun onResponse(
                call: Call<WrappedResponse<Posko>>,
                response: Response<WrappedResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast("Terjadi kesalahan")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast(response.message())
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Posko>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
            }

        })
    }


    override fun destroy() {
        view = null
    }
}