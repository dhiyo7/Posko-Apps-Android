package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.LogistikKeluarActivityContract
import com.adit.poskoapp.models.LogistikKeluar
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdateLogistikKeluarActivityPresenter(v : LogistikKeluarActivityContract.CreateOrUpdateView?) : LogistikKeluarActivityContract.CreateOrUpdateInteraction {

    private var view : LogistikKeluarActivityContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()

    override fun create(
        token: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        id_posko_penerima: String,
        status: String,
        tanggal: String,
        satuan: String
    ) {
        val request = api.postLogistikKeluar(token, jenis_kebutuhan, keterangan, jumlah, id_posko_penerima, status, tanggal, satuan)
        request.enqueue(object : Callback<WrappedResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikKeluar>>,
                response: Response<WrappedResponse<LogistikKeluar>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast(body?.message)
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Terjadi kesalahan")
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikKeluar>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        id_posko_penerima: String,
        status: String,
        tanggal: String,
        satuan: String
    ) {
        val request = api.putLogistikKeluar(token, id, jenis_kebutuhan, keterangan, jumlah, id_posko_penerima, status, tanggal, satuan)
        request.enqueue(object : Callback<WrappedResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikKeluar>>,
                response: Response<WrappedResponse<LogistikKeluar>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast(body?.message)
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast("Terjadi kesalahan")
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikKeluar>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun getPosko() {
        val request = api.getPosko()
        request.enqueue(object : Callback<WrappedListResponse<Posko>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Posko>>,
                response: Response<WrappedListResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        println("POSKO " + body.data)
                        view?.attachToSpinner(body.data)

                    }
                }else{
                    view?.showToast("Terjadi kesalahan")
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Posko>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
            }

        })
    }

    override fun destroy() {
        view = null
    }


}