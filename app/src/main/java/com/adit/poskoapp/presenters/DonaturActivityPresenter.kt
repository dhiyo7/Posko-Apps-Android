package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.DonaturActivityContract
import com.adit.poskoapp.models.Donatur
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonaturActivityPresenter(v: DonaturActivityContract.DonaturActivityView?) : DonaturActivityContract.DonaturActivityPresenter {
    private var view : DonaturActivityContract.DonaturActivityView? = v
    private var api = PoskoAPI.instance()

    override fun infoDonatur() {
        val request = api.getDonatur()
        view?.hideDataEmpty()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Donatur>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Donatur>>,
                response: Response<WrappedListResponse<Donatur>>
            ) {
                if(response.isSuccessful){
                    val body = response.body();
                    if(body !== null){
                        if(body.data.size == 0){
                            view?.showDataEmpty()
                        }else{
                            view?.attachDonaturRecycler(body.data)
                        }
                        view?.hideLoading()
                    }else{
                        view?.showToast("Something went wrong, try again later")
                        view?.hideLoading()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Donatur>>, t: Throwable) {
                view?.showToast("Cannot connect to server")
                println(t.message)
                view?.hideLoading()
            }
        })
    }

    override fun delete(token: String, id: String) {
        val request = api.deleteDonatur(token, id)
        request.enqueue(object : Callback<WrappedResponse<Donatur>>{
            override fun onResponse(
                call: Call<WrappedResponse<Donatur>>,
                response: Response<WrappedResponse<Donatur>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        infoDonatur()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Donatur>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
            }


        })
    }

    override fun destroy() {
        view = null
    }

}