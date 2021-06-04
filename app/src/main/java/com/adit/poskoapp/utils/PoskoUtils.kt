package com.adit.poskoapp.utils

import android.content.Context

class PoskoUtils {
    companion object {
        var API_ENDPOINT = "https://logistik-brebes.herokuapp.com/"

        fun getToken(context: Context): String? {
            val token = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            return token?.getString("TOKEN", null)
        }

        fun setToken(context: Context, token: String) {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                apply()
            }
        }

        fun clearToken(context: Context) {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun isValidPassword(password: String) = password.length > 6
    }
}