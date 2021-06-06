package com.example.melongarden.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.melongarden.Helper
import com.example.melongarden.R
import com.example.melongarden.adapter.CommentItemAdapter
import com.example.melongarden.bean.CommentBean
import com.example.melongarden.bean.ReplyCommentBean
import com.example.melongarden.service.NetHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity(), View.OnClickListener {
    private val adapter = CommentItemAdapter()
    private var postId: Int = 0
    private var sharePreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        postId = intent.getStringExtra(Helper.POST_ID)?.toInt() ?: 0
        updateData(postId)
        commentRecycleView.adapter = adapter
        commentRecycleView.layoutManager = LinearLayoutManager(this)
        postCommentBtn.setOnClickListener(this)
        sharePreferences = this.getSharedPreferences("login", MODE_PRIVATE)
    }

    private fun updateData(x: Int) {
        NetHelper.getRequest().getComment(x)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CommentBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CommentBean) {
                    setData(t)
                }

                override fun onError(e: Throwable) {
                    Log.i("lwt", "网络请求错误")
                }

                override fun onComplete() {

                }

            })
    }

    private fun setData(data: CommentBean) {
        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val text = commentPostText.text.toString()
        NetHelper.getRequest()
            .postComment(sharePreferences?.getString("token", "") ?: "", text, postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ReplyCommentBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ReplyCommentBean) {
                    Log.i("lwt", t.comment_id)
                }

                override fun onError(e: Throwable) {
                    Log.i("lwt", e.message.toString())
                }

                override fun onComplete() {
                    commentPostText.setText("")
                    updateData(postId)
                }
            })
    }
}