package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.DonaturActivityContract
import com.adit.poskoapp.models.Donatur
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdateDonaturActivityPresenter(v: DonaturActivityContract.CreateOrUpdateView?) : DonaturActivityContract.CreateOrUpdateInteraction {

    private var view : DonaturActivityContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()

    override fun create(
        token: String,
        nama: String,
        id_posko: String,
        jenis_kebutuhan: String,
        keterangan: String,
        alamat: String,
        tanggal: String
    ) {
        val request = api.postDonatur(token, nama, id_posko, jenis_kebutuhan, keterangan, alamat, tanggal)
        request.enqueue(object : Callback<WrappedResponse<Donatur>> {
            override fun onResponse(
                call: Call<WrappedResponse<Donatur>>,
                response: Response<WrappedResponse<Donatur>>
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

            override fun onFailure(call: Call<WrappedResponse<Donatur>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        nama: String,
        id_posko: String,
        jenis_kebutuhan: String,
        keterangan: String,
        alamat: String,
        tanggal: String
    ) {
        val request = api.putDonatur(token, id, nama, id_posko, jenis_kebutuhan, keterangan, alamat, tanggal)
        request.enqueue(object : Callback<WrappedResponse<Donatur>>{
            override fun onResponse(
                call: Call<WrappedResponse<Donatur>>,
                response: Response<WrappedResponse<Donatur>>
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

            override fun onFailure(call: Call<WrappedResponse<Donatur>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
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