package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.BencanaActivityContract
import com.adit.poskoapp.models.Bencana
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BencanaActivityPresenter(v: BencanaActivityContract.View?) :
    BencanaActivityContract.Interaction {

    private var view: BencanaActivityContract.View? = v
    private var api = PoskoAPI.instance()

    override fun allBencana() {
        api.getBencana().enqueue(object : Callback<WrappedListResponse<Bencana>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Bencana>>,
                response: Response<WrappedListResponse<Bencana>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == 200) {
                        view?.attachToRecycle(body.data)
                        println("DATA : ${body.data}")
                    } else {
                        view?.toast("ada yang tidak beres")
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Bencana>>, t: Throwable) {
                println("Log: ${t.message} ")
                view?.toast("Cannot connect to server")
            }
        })
    }

    override fun destroy() {
        view = null
    }
}