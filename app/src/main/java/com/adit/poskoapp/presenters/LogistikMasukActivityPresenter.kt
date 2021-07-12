package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.LogistikMasukActivityContract
import com.adit.poskoapp.models.LogistikMasuk
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogistikMasukActivityPresenter(v : LogistikMasukActivityContract.LogistikMasukActivityView?) : LogistikMasukActivityContract.LogistikMasukPresenter {
    private var view : LogistikMasukActivityContract.LogistikMasukActivityView? = v
    private var api = PoskoAPI.instance()

    override fun getLogistikMasuk(token: String) {
        val request = api.getLogistikMasuk(token)
        view?.hideDataEmpty()
        view?.showLoading()
        request.enqueue(object: Callback<WrappedListResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedListResponse<LogistikMasuk>>,
                response: Response<WrappedListResponse<LogistikMasuk>>
            ) {
                if(response.isSuccessful){
                    val body = response.body();
                    if(body !== null){
                        if(body.data.size == 0){
                            view?.showDataEmpty()
                        }else{
                            view?.attachLogistikMasukRecycler(body.data)
                        }
                        view?.hideLoading()
                    }else{
                        view?.showToast("Something went wrong, try again later")
                        view?.hideLoading()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Cannot connect to server")
                println(t.message)
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }
}