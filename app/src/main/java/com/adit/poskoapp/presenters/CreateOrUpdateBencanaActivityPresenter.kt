package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.BencanaActivityContract
import com.adit.poskoapp.models.Bencana
import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdateBencanaActivityPresenter(v: BencanaActivityContract.ViewCreate?) : BencanaActivityContract.InteractionPost {

    private var view : BencanaActivityContract.ViewCreate? = v
    private var api = PoskoAPI.instance()

    override fun create(
        token: String,
        name: RequestBody,
        foto: MultipartBody.Part,
        detail: RequestBody,
        date: RequestBody
    ) {
        val request = api.postBencana(token,name, foto, detail, date)
        request.enqueue(object : Callback<WrappedResponse<Bencana>>{
            override fun onResponse(
                call: Call<WrappedResponse<Bencana>>,
                response: Response<WrappedResponse<Bencana>>
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

            override fun onFailure(call: Call<WrappedResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                t.printStackTrace()
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        name: RequestBody,
        foto: MultipartBody.Part,
        detail: RequestBody,
        date: RequestBody,
        method: RequestBody
    ) {
        val request = api.editBencana(token, id.toInt(), name, foto, detail, date, method)
        request.enqueue(object : Callback<WrappedResponse<Bencana>>{
            override fun onResponse(
                call: Call<WrappedResponse<Bencana>>,
                response: Response<WrappedResponse<Bencana>>
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

            override fun onFailure(call: Call<WrappedResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                t.printStackTrace()
            }

        })
    }

    override fun updateTanpaFoto(
        token: String,
        id: String,
        name: RequestBody,
        detail: RequestBody,
        date: RequestBody,
        method: RequestBody
    ) {
        val request = api.editBencanaTanpaFoto(token, id.toInt(), name, detail, date, method)
        request.enqueue(object : Callback<WrappedResponse<Bencana>>{
            override fun onResponse(
                call: Call<WrappedResponse<Bencana>>,
                response: Response<WrappedResponse<Bencana>>
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

            override fun onFailure(call: Call<WrappedResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                t.printStackTrace()
            }

        })
    }


    override fun destroy() {
        view = null
    }
}