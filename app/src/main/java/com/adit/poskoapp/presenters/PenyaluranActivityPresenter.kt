package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.PenyaluranActivityContract
import com.adit.poskoapp.models.Penyaluran
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenyaluranActivityPresenter(v : PenyaluranActivityContract.PenyaluranActivityView?) : PenyaluranActivityContract.PenyaluranActivityPresenter {
    private var view : PenyaluranActivityContract.PenyaluranActivityView? = v
    private var api = PoskoAPI.instance()
    override fun infoPenyaluran(token: String) {
        view?.hideDataEmpty()
        view?.showLoading()
        val request = api.getPenyaluran(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Penyaluran>>,
                response: Response<WrappedListResponse<Penyaluran>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.size == 0){
                            view?.showDataEmpty()
                        }else{
                            view?.attachPenyaluranRecycler(body.data)
                        }
                        view?.hideLoading()
                    }else{
                        view?.showToast("Terjadi Kesalahan")
                    }
                }else{
                    view?.showToast(response.message())
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak terkoneksi dengan server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = api.deletePenyaluran(token, id)
        with(request) {
            enqueue(object : Callback<WrappedResponse<Penyaluran>>{
                override fun onResponse(
                    call: Call<WrappedResponse<Penyaluran>>,
                    response: Response<WrappedResponse<Penyaluran>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            view?.showToast(body.message!!)
                            infoPenyaluran(token!!)
                            view?.hideLoading()
                        }else{
                            view?.showToast(body?.message!!)
                            view?.hideLoading()
                        }
                    }else{
                        view?.showToast("Terjadi Kesalahan")
                        println(response.message())
                    }
                }

                override fun onFailure(call: Call<WrappedResponse<Penyaluran>>, t: Throwable) {
                    view?.showToast("Tidak terkoneksi dengan server")
                    view?.hideLoading()
                    println(t.message)
                }

            })
        }
    }

    override fun destroy() {
        view = null
    }
}