package com.example.melongarden.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.melongarden.Helper
import com.example.melongarden.R
import com.example.melongarden.adapter.PostsItemAdapter
import com.example.melongarden.bean.PostBean
import com.example.melongarden.service.NetHelper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var dataBean: PostBean? = null
    private val adapter = PostsItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
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

    private fun initData() {

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
}