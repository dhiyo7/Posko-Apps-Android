package com.adit.poskoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adit.poskoapp.contracts.LoginActivityContract
import com.adit.poskoapp.presenters.LoginActivityPresenter
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginActivityContract.View {
    private var presenter = LoginActivityPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        isLoggedIn()
        presenter = LoginActivityPresenter(this)
        doLogin()
//        moveRegister()
    }

//    private fun moveRegister() {
//        TODO("Not yet implemented")
//    }

    private fun doLogin() {
        btnLogin.setOnClickListener {
            val email = etId.text.toString().trim()
            val pass = etPass.text.toString().trim()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if (pass.length > 5) {
                    presenter.login(email, pass)
                } else {
                    toast("Cek password anda")
                }
            } else {
                toast("Isi semua form")
            }
        }
    }

    private fun isLoggedIn() {
        val token = PoskoUtils.getToken(this)
        if (token != null) {
            startActivity(Intent(this, MainActivity::class.java)).also { finish() }
        }
    }

    override fun toast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun success(token: String, level: String) {
        PoskoUtils.setToken(this, "Bearer ${token}")
        println("Token "+token)
        if(level == "Admin"){
            startActivity(Intent(this, MainActivity::class.java)).also { finish() }
        }else{
            startActivity(Intent(this, PetugasBerandaActivity::class.java)).also { finish() }
        }


    }

    override fun isLoading(state: Boolean) {
        progress.visibility= View.VISIBLE
        btnLogin.isEnabled = !state
    }

    override fun idError(err: String?) {
        inId.error = err
    }

    override fun passwordError(err: String?) {
        inPass.error = err
    }

    override fun notConect() {
        btnLogin.isEnabled = true
        progress.isEnabled = false    }
}