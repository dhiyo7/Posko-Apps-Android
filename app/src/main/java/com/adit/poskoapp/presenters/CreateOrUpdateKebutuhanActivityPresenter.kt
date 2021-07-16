package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.KebutuhanLogistikActivityContract
import com.adit.poskoapp.models.KebutuhanLogistik
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdateKebutuhanActivityPresenter(v : KebutuhanLogistikActivityContract.CreateOrUpdateView?) : KebutuhanLogistikActivityContract.CreateOrUpdateInteraction {
    private var view : KebutuhanLogistikActivityContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()
    override fun create(
        token: String,
        id_posko: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        status: String,
        tanggal: String,
        satuan : String,
    ) {
        val request = api.postKebutuhan(token, id_posko, jenis_kebutuhan, keterangan, jumlah, status, tanggal, satuan)
        request.enqueue(object : Callback<WrappedResponse<KebutuhanLogistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<KebutuhanLogistik>>,
                response: Response<WrappedResponse<KebutuhanLogistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        view?.hideLoading()
                        view?.success()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<KebutuhanLogistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                t.printStackTrace()
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        id_posko: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        status: String,
        tanggal: String,
        satuan : String,
    ) {
        val request = api.putKebutuhan(token, id, id_posko, jenis_kebutuhan, keterangan, jumlah, status, tanggal, satuan)
        request.enqueue(object : Callback<WrappedResponse<KebutuhanLogistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<KebutuhanLogistik>>,
                response: Response<WrappedResponse<KebutuhanLogistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        view?.hideLoading()
                        view?.success()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<KebutuhanLogistik>>, t: Throwable) {
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
                        view?.setSelectionSpinner(body.data)

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