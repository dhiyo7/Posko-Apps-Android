package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.PoskoActivityContract
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoskoActivityPresenter(v: PoskoActivityContract.View?) : PoskoActivityContract.Interaction {

    private var view: PoskoActivityContract.View? = v
    private var api = PoskoAPI.instance()

    override fun allPosko() {
        api.getPosko().enqueue(object : Callback<WrappedListResponse<Posko>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Posko>>,
                response: Response<WrappedListResponse<Posko>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == 200) {
                        view?.attachToRecycle(body.data)
                        println("DATA POSKO : ${body.data}")
                    } else {
                        view?.toast("ada yang tidak beres")
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Posko>>, t: Throwable) {
                println("Log: ${t.message} ")
                view?.toast("Cannot connect to server")
            }

        })
    }

    override fun destroy() {
        view = null
    }
}