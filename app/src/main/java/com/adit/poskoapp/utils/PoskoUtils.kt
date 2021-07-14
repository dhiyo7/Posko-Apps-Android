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

        fun setList(context: Context, value : String){
            val pref = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("USERDATA", value)
                apply()
            }
        }

        fun getList(context: Context) : String?{
            val list = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE)
            return list?.getString("USERDATA", null)
        }

        fun clearToken(context: Context) {
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun isValidPassword(password: String) = password.length > 6
    }
}