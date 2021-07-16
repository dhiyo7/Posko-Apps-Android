package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.BencanaActivityContract
import com.adit.poskoapp.contracts.PenerimaanLogisitkContract
import com.adit.poskoapp.models.PenerimaanLogistik
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenerimaanLogistikActivityPresenter(v :  PenerimaanLogisitkContract.PenerimaanLogistikActivityView?) : PenerimaanLogisitkContract.PenerimmaanLogistikPresenter {
    private var view: PenerimaanLogisitkContract.PenerimaanLogistikActivityView? = v
    private var api = PoskoAPI.instance()

    override fun infoPenerimaanLogistik() {
        val request = api.getPenerimaanLogistik()
        view?.hideDataEmpty()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<PenerimaanLogistik>>{
            override fun onResponse(
                call: Call<WrappedListResponse<PenerimaanLogistik>>,
                response: Response<WrappedListResponse<PenerimaanLogistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.size == 0){
                            view?.showDataEmpty()
                        }else{
                            view?.attachPenerimaanLogistikRecycler(body.data)
                        }

                        view?.hideLoading()
                    }else{
                        view?.showToast("Something went wrong, please try again later")
                        view?.hideLoading()
                    }
                }
            }

            override fun onFailure(
                call: Call<WrappedListResponse<PenerimaanLogistik>>,
                t: Throwable
            ) {
                view?.showToast("Can't connect to Server")
                println(t.message)
                view?.hideLoading()
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = api.deletePenerimaanLogistik(token, id)
        request.enqueue(object : Callback<WrappedResponse<PenerimaanLogistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<PenerimaanLogistik>>,
                response: Response<WrappedResponse<PenerimaanLogistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        infoPenerimaanLogistik()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<PenerimaanLogistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }

}