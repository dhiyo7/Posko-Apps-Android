package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.KebutuhanLogistikActivityContract
import com.adit.poskoapp.models.KebutuhanLogistik
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KebutuhanLogistikActivityPresenter(v : KebutuhanLogistikActivityContract.KebutuhanLogistikActivityView?) : KebutuhanLogistikActivityContract.KebutuhanLogistikPresenter {
    private var view : KebutuhanLogistikActivityContract.KebutuhanLogistikActivityView? = v
    private var api = PoskoAPI.instance()

    override fun infoKebutuhanLogistik(token : String) {
        view?.hideDataEmpty()
        val request = api.getKebutuhan(token)
        request.enqueue(object : Callback<WrappedListResponse<KebutuhanLogistik>>{
            override fun onResponse(
                call: Call<WrappedListResponse<KebutuhanLogistik>>,
                response: Response<WrappedListResponse<KebutuhanLogistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        if(body.data.size == 0){
                            view?.showDataEmpty()
                        }else{
                            view?.attachKebutuhanLogistikRecycler(body.data)
                        }
                        view?.hideLoading()
                    }else{
                        view?.showToast("Terjadi Kesalahan")
                    }
                }
            }

            override fun onFailure(
                call: Call<WrappedListResponse<KebutuhanLogistik>>,
                t: Throwable
            ) {
                view?.showToast("Tidak terkoneksi dengan server")
            }

        })

    }

    override fun delete(token: String, id: String) {
        val request = api.deleteKebutuhan(token, id)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<KebutuhanLogistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<KebutuhanLogistik>>,
                response: Response<WrappedResponse<KebutuhanLogistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message!!)
                        infoKebutuhanLogistik(token)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<KebutuhanLogistik>>, t: Throwable) {
                view?.showToast("Terjadi kesalahan")
            }

        })
    }

    override fun destroy() {
        view = null
    }


}