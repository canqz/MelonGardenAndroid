package com.example.melongarden

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.melongarden.adapter.PostsItemAdapter
import com.example.melongarden.bean.PostBean
import com.example.melongarden.service.PostService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var dataBean: PostBean? = null
    private val adapter = PostsItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initRecycleView()
        setData()
    }

    private fun setData() {
        dataBean?.apply {
            adapter.setData(this.posts)
        }
        adapter.notifyDataSetChanged()
    }

    private fun initRecycleView() {
        postsRecycleView.adapter = adapter
        postsRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private fun initData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://81.68.104.78:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val request = retrofit.create(PostService::class.java)

        val observable = request.getPosts()

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PostBean> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: PostBean) {
                    dataBean = t
                    Log.i("lwt", "hello ${dataBean?.total_posts}")
                }

                override fun onError(e: Throwable) {
                    Log.i("lwt", "报错$e")
                }

            })
    }
}