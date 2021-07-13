package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.PetugasActivityContract
import com.adit.poskoapp.models.Petugas
import com.adit.poskoapp.models.Posko
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedListResponse
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAndUpdatePetugasActivityPresenter(v : PetugasActivityContract.CreateOrUpdateView?) : PetugasActivityContract.CreateOrUpdatePresenter {

    private var view : PetugasActivityContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()
    override fun create(
        token: String,
        nama: String,
        username: String,
        password: String,
        konfirmasi_password: String,
        level: String,
        id_posko: String
    ) {
        val request = api.createPetugasPosko(token, nama, username, password,konfirmasi_password, level, id_posko)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<Petugas>>{
            override fun onResponse(
                call: Call<WrappedResponse<Petugas>>,
                response: Response<WrappedResponse<Petugas>>
            ) {
                println("RESPONSE "+ response.body())
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        view?.hideLoading()
                        view?.success()
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

    override fun update(
        token: String,
        id: String,
        nama: String,
        username: String,
        password: String,
        konfirmasi_password: String,
        level: String,
        id_posko: String
    ) {
        val request = api.updatePetugasPosko(token, id, nama, username, password, konfirmasi_password, level, id_posko)
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
                        view?.success()
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

    override fun getPosko() {
        val request = api.getPosko()
        request.enqueue(object : Callback<WrappedListResponse<Posko>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Posko>>,
                response: Response<WrappedListResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToSpinner(body.data)
                        view?.setSelectionSpinner(body.data)

                    }
                }else{
                    view?.showToast("Terjadi kesalahan")
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Posko>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
            }


        })
    }


    override fun destroy() {
        view = null
    }
}