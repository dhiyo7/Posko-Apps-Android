package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.PenerimaanLogisitkContract
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdatePenerimaanActivityPresenter(v : PenerimaanLogisitkContract.CreateOrUpdateView?) : PenerimaanLogisitkContract.CreateOrUpdateInteraction {

    private var view : PenerimaanLogisitkContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()
    override fun create(
        token: String,
        id_posko: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        satuan: String,
        status: String,
        tanggal: String
    ) {
        val request = api.postPenerimaanLogistik(token, id_posko, jenis_kebutuhan, keterangan, jumlah, satuan, status, tanggal)
        request.enqueue(object : Callback<WrappedResponse<PenerimaanLogistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<PenerimaanLogistik>>,
                response: Response<WrappedResponse<PenerimaanLogistik>>
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

            override fun onFailure(call: Call<WrappedResponse<PenerimaanLogistik>>, t: Throwable) {
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
        satuan: String,
        status: String,
        tanggal: String
    ) {
        val request = api.putPenerimaanLogistik(token, id, id_posko, jenis_kebutuhan, keterangan, jumlah, satuan, status, tanggal)
        request.enqueue(object : Callback<WrappedResponse<PenerimaanLogistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<PenerimaanLogistik>>,
                response: Response<WrappedResponse<PenerimaanLogistik>>
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

            override fun onFailure(call: Call<WrappedResponse<PenerimaanLogistik>>, t: Throwable) {
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