package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.LogistikKeluarActivityContract
import com.adit.poskoapp.models.LogistikKeluar
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogistikKeluarActivityPresenter(v : LogistikKeluarActivityContract.LogistikKeluarActivityView?) : LogistikKeluarActivityContract.LogistikKeluarPresenter{
    private var view : LogistikKeluarActivityContract.LogistikKeluarActivityView? = v
    private var api = PoskoAPI.instance()
    override fun getLogistikKeluar(token: String) {
        val request = api.getLogistikKeluar(token)
        view?.hideDataEmpty()
        view?.showLoading()
        request.enqueue(object: Callback<WrappedListResponse<LogistikKeluar>> {
            override fun onResponse(
                call: Call<WrappedListResponse<LogistikKeluar>>,
                response: Response<WrappedListResponse<LogistikKeluar>>
            ) {
                if(response.isSuccessful){
                    val body = response.body();
                    if(body !== null){
                        if(body.data.size == 0){
                            view?.showDataEmpty()
                        }else{
                            view?.attachLogistikKeluarRecycler(body.data)
                        }
                        view?.hideLoading()
                    }else{
                        view?.showToast("Something went wrong, try again later")
                        view?.hideLoading()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<LogistikKeluar>>, t: Throwable) {
                view?.showToast("Cannot connect to server")
                println(t.message)
                view?.hideLoading()
            }
        })

    }

    override fun delete(token: String, id: String) {
        val request = api.deleteLogistikKeluar(token, id)
        request.enqueue(object : Callback<WrappedResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikKeluar>>,
                response: Response<WrappedResponse<LogistikKeluar>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message!!)
                        view?.hideLoading()
                        getLogistikKeluar(token)
                    }else{
                        view?.showToast("Terjadi kesalahan")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast(response?.body()?.message.toString())
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikKeluar>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}