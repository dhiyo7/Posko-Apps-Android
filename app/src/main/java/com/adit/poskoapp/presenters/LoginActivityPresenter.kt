package com.adit.poskoapp.presenters

import android.util.Log
import com.adit.poskoapp.contracts.LoginActivityContract
import com.adit.poskoapp.models.User
import com.adit.poskoapp.utils.PoskoUtils
import com.adit.poskoapp.webservices.PoskoAPI
import com.adit.poskoapp.webservices.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(v: LoginActivityContract.View) : LoginActivityContract.Interaction {

    private var view: LoginActivityContract.View? = v
    private var api = PoskoAPI.instance()

    override fun validate(id: String, password: String): Boolean {
        view?.passwordError(null)
        if (!PoskoUtils.isValidPassword(password)) {
            view?.passwordError("Password tidak valid")
            return false
        }
        return true
    }

    override fun login(username: String, password: String) {
        view?.isLoading(true)
        api.login(username, password).enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == 200) {
                        view?.toast("Selamat datang ${body.data!!.name}")
                        view?.success(body.data?.token!!)
                    } else {
                        view?.toast("Login gagal, cek email dan password")
                    }
                } else {
                    Log.e("Wew", response.message())
                    view?.toast("Ada yang tidak beres, coba lagi nanti, atau hubungi admin")
                }
                view?.isLoading(false)
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.toast("Koneksi ke server tidak bisa")
                println("error " + t.message.toString())
                view?.notConect()
            }

        })
    }

    override fun register(email: String, name: String, password: String, confirm_password: String) {
        view?.isLoading(true)
        api.register(email, name, password, confirm_password)
            .enqueue(object : Callback<WrappedResponse<User>> {
                override fun onResponse(
                    call: Call<WrappedResponse<User>>,
                    response: Response<WrappedResponse<User>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.status == 200) {
                            view?.toast("Selamat Datang ${body.data!!.name}")
                            view?.success(body.data?.token!!)
                        } else {
                            view?.toast("Register Gagal, cek semua form")
                        }
                    } else {
                        view?.toast("Ada yang tidak beres")
                    }
                    view?.isLoading(false)
                }

                override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                    view?.toast("Koneksi ke server tidak bisa")
                    println("error " + t.message)
                    view?.notConect()
                }

            })
    }

    override fun destroy() {
        view = null
    }
}