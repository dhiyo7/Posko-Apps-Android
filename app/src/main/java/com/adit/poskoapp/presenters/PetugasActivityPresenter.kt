package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.PetugasActivityContract
import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetugasActivityPresenter(v: PetugasActivityContract.PetugasActivityView?) : PetugasActivityContract.PetugasActivityPresenter {

    private var view : PetugasActivityContract.PetugasActivityView? = v
    private var api = PoskoAPI.instance()

    override fun infoPetugas(token: String) {
        val request = api.getPetugasPosko(token)
        view?.showLoading()
        view?.hideDataEmpty()

        request.enqueue(object : Callback<WrappedListResponse<Petugas>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Petugas>>,
                response: Response<WrappedListResponse<Petugas>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.size == 0){
                            view?.showDataEmpty()
                        }else{
                            view?.attachPetugasRecycler(body.data)
                        }

                        view?.hideLoading()
                    }else{
                        view?.showToast("Something went wrong, please try again later")
                        view?.hideLoading()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Petugas>>, t: Throwable) {
                view?.showToast("Can't connect to Server")
                println(t.message)
                view?.hideLoading()
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = api.deletePetugasPosko(token, id)
        view?.showLoading()

        request.enqueue(object : Callback<WrappedResponse<Petugas>>{
            override fun onResponse(
                call: Call<WrappedResponse<Petugas>>,
                response: Response<WrappedResponse<Petugas>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        view?.hideLoading()
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Petugas>>, t: Throwable) {
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