package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.LogistikMasukActivityContract
import com.adit.poskoapp.models.LogistikMasuk
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdateLogistikMasukActivityPresenter(v: LogistikMasukActivityContract.CreateOrUpdateView?) :  LogistikMasukActivityContract.CreateOrUpdateInteraction {

    private var view : LogistikMasukActivityContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()

    override fun create(
        token: String,
        jenis_kebutuhan: RequestBody,
        keterangan : RequestBody,
        jumlah : RequestBody,
        pengirim : RequestBody,
        posko_penerima : RequestBody,
        tanggal: RequestBody,
        status: RequestBody,
        satuan : RequestBody,
        foto : MultipartBody.Part
    ) {
        val request = api.postLogistikMasuk(token, jenis_kebutuhan, keterangan, jumlah,pengirim, posko_penerima, tanggal, status, satuan, foto)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
            ) {
                println("ERROR " + response)
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }
                }else{

                    view?.showToast("Terjadi kesalahan")
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                t.printStackTrace()
            }

        })
    }

    override fun update(
        token: String,
        id: Int,
        jenis_kebutuhan: RequestBody,
        keterangan: RequestBody,
        jumlah: RequestBody,
        pengirim: RequestBody,
        posko_penerima: RequestBody,
        tanggal: RequestBody,
        status: RequestBody,
        satuan: RequestBody,
        foto: MultipartBody.Part,
        method: RequestBody
    ) {
        val request = api.putLogistikMasuk(token, id, jenis_kebutuhan, keterangan, jumlah,pengirim, posko_penerima, tanggal, status, satuan, foto, method)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
            ) {
                println("ERROR " + response.body())
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }
                }else{
                    view?.showToast("Terjadi kesalahan")
                    view?.hideLoading()

                }
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                t.printStackTrace()
            }

        })
    }

    override fun updateTanpaFoto(
        token: String,
        id: Int,
        jenis_kebutuhan: RequestBody,
        keterangan: RequestBody,
        jumlah: RequestBody,
        pengirim: RequestBody,
        posko_penerima: RequestBody,
        tanggal: RequestBody,
        status: RequestBody,
        satuan: RequestBody,
        method: RequestBody
    ) {
        val request = api.putLogistikMasukTanpaFoto(token, id, jenis_kebutuhan, keterangan, jumlah,pengirim, posko_penerima, tanggal, status, satuan, method)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
            ) {
                println("ERROR " + response)
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }
                }else{
                    view?.showToast("Terjadi kesalahan")
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                t.printStackTrace()
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