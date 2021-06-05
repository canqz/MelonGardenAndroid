package com.example.melongarden.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.melongarden.R
import com.example.melongarden.bean.Token
import com.example.melongarden.bean.TokenBean
import com.example.melongarden.service.NetHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signInBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signUpBtn -> signUp()
            R.id.signInBtn -> signIn()
        }
    }

    private fun signIn() {
        Log.i("lwt", "signIn")
    }

    private fun signUp() {
        val id = idText.text.toString()
        val password = passwordText.text.toString()
        val observable = NetHelper.getRequest().login(id, password)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TokenBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: TokenBean) {
                    Log.i("lwt", t.token)
                    Token.string = t.token
                }

                override fun onError(e: Throwable) {
                    Log.i("lwt", e.message.toString())
                    Toast.makeText(this@LoginActivity, "password error!", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            })
    }
}