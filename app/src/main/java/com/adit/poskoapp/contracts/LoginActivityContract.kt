package com.adit.poskoapp.contracts

interface LoginActivityContract {
    /* Main funtion untuk terhubung ke View */
    interface View {
        fun toast(message: String)
        fun success(token: String, level: String)
        fun isLoading(state: Boolean)
        fun idError(err: String?)
        fun passwordError(err: String?)
        fun notConect()
    }

    /* Main function untuk Logic (Connection to API , etc) dan nantinya akan
   * di teruskan di Presenter sebagai Base Logic funtion di setiap Activity Viewnya */
    interface Interaction {
        fun validate(id: String, password: String): Boolean
        fun login(username: String, password: String)
        fun register(email: String, name: String, password: String, confirm_password: String)
        fun destroy()
    }
}