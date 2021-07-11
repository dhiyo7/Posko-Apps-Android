package com.adit.poskoapp.utils

import android.content.Context

class PoskoUtils {
    companion object {
        var API_ENDPOINT = "https://logistik-brebes.herokuapp.com/"

        fun getToken(context: Context): String? {
            val token = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            return token?.getString("TOKEN", null)
        }

        fun getLevel (context: Context) : String? {
            val level = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            return level?.getString("LEVEL", null)
        }

        fun setToken(context: Context, token: String, level : String) {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                putString("LEVEL", level)
                apply()
            }
        }

        fun setObjectUserLogin(){

        }

        fun clearToken(context: Context) {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun isValidPassword(password: String) = password.length > 6
    }
}