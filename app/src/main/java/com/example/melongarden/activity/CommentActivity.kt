package com.example.melongarden.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.melongarden.Helper
import com.example.melongarden.R
import com.example.melongarden.adapter.CommentItemAdapter
import com.example.melongarden.bean.CommentBean
import com.example.melongarden.service.NetHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {
    private val adapter = CommentItemAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        val x = intent.getStringExtra(Helper.POST_ID)?.toInt() ?: 0
        initData(x)
        commentRecycleView.adapter = adapter
        commentRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(x: Int) {
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
                    Log.i("lwt","网络请求错误")
                }

                override fun onComplete() {

                }

            })
    }

    private fun setData(data: CommentBean) {
        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }
}