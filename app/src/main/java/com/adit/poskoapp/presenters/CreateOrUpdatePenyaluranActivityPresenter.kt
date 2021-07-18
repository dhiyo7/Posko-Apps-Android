package com.adit.poskoapp.presenters

import com.adit.poskoapp.contracts.PenyaluranActivityContract
import com.adit.poskoapp.models.Penyaluran
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateOrUpdatePenyaluranActivityPresenter(v : PenyaluranActivityContract.CreateOrUpdateView?) : PenyaluranActivityContract.CreateOrUpdateInteraction {
    private var view : PenyaluranActivityContract.CreateOrUpdateView? = v
    private var api = PoskoAPI.instance()

    override fun create(
        token: String,
        nama_penerima: RequestBody,
        jenis_kebutuhan: RequestBody,
        jumlah: RequestBody,
        satuan: RequestBody,
        status: RequestBody,
        keterangan: RequestBody,
        alamat: RequestBody,
        tanggal: RequestBody,
        foto: MultipartBody.Part
    ) {
        val request = api.postPenyaluran(token, nama_penerima, jenis_kebutuhan, jumlah, satuan, status, keterangan, alamat, tanggal, foto)
        request.enqueue(object : Callback<WrappedResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedResponse<Penyaluran>>,
                response: Response<WrappedResponse<Penyaluran>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body?.message!!)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast(response.message())
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun update(
        token: String,
        id: String,
        nama_penerima: RequestBody,
        jenis_kebutuhan: RequestBody,
        jumlah: RequestBody,
        satuan: RequestBody,
        status: RequestBody,
        keterangan: RequestBody,
        alamat: RequestBody,
        tanggal: RequestBody,
        foto: MultipartBody.Part,
        method: RequestBody
    ) {
        val request = api.putPenyaluran(token, id, nama_penerima, jenis_kebutuhan, jumlah, satuan, status, keterangan, alamat, tanggal, foto, method)
        request.enqueue(object : Callback<WrappedResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedResponse<Penyaluran>>,
                response: Response<WrappedResponse<Penyaluran>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body?.message!!)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast(response.message())
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun updateTanpaFoto(
        token: String,
        id: String,
        nama_penerima: RequestBody,
        jenis_kebutuhan: RequestBody,
        jumlah: RequestBody,
        satuan: RequestBody,
        status: RequestBody,
        keterangan: RequestBody,
        alamat: RequestBody,
        tanggal: RequestBody,
        method: RequestBody
    ) {
        val request = api.putPenyaluranTanpaFoto(token, id, nama_penerima, jenis_kebutuhan, jumlah, satuan, status, keterangan, alamat, tanggal, method)
        request.enqueue(object : Callback<WrappedResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedResponse<Penyaluran>>,
                response: Response<WrappedResponse<Penyaluran>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body?.message!!)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast(response.message())
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })

    }

    override fun destroy() {
        view = null
    }
}