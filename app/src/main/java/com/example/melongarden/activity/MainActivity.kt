package com.example.melongarden.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.melongarden.Helper
import com.example.melongarden.R
import com.example.melongarden.adapter.PostsItemAdapter
import com.example.melongarden.bean.PostBean
import com.example.melongarden.bean.ReplyPostBean
import com.example.melongarden.service.NetHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener {
    private var dataBean: PostBean? = null
    private val adapter = PostsItemAdapter()
    private var sharePreference: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharePreference = getSharedPreferences("login", MODE_PRIVATE)
        loginOutBtn.setOnClickListener(this)
        sendPostsBtn.setOnClickListener(this)
        postContentEt.onFocusChangeListener = this
        updateData()
        initRecycleView()
    }

    private fun setData() {
        dataBean?.apply {
            adapter.setData(this)
        }
        adapter.notifyDataSetChanged()
    }

    private fun initRecycleView() {
        adapter.setOnItemClickListener(object : PostsItemAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommentActivity::class.java)
                intent.putExtra(Helper.POST_ID, dataBean?.posts?.get(position)?.id.toString())
                startActivity(intent)
            }
        })
        postsRecycleView.adapter = adapter
        postsRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private fun updateData() {

        val observable = NetHelper.getRequest().getPosts()

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PostBean> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: PostBean) {
                    dataBean = t
                    setData()
                }

                override fun onError(e: Throwable) {
                    Log.i("lwt", e.toString())
                }
            })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.loginOutBtn -> {
                editor = sharePreference?.edit()
                editor?.putString("token", "")
                editor?.apply()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.sendPostsBtn -> {
                sendPost()
                postTitle.visibility = View.GONE
            }
        }
    }

    private fun sendPost() {
        val token = sharePreference?.getString("token", "") ?: ""
        val title = postTitle.text.toString()
        val content = postContentEt.text.toString()
        NetHelper.getRequest().postPosts(token, content, title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ReplyPostBean> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ReplyPostBean) {
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, "network error", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    updateData()
                }

            })
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        when (v.id) {
            R.id.postContentEt -> {
                if (hasFocus) {
                    postTitle.visibility = View.VISIBLE
                }
            }
        }
    }
}