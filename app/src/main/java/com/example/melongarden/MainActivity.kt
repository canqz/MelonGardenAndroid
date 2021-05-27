package com.example.melongarden

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.melongarden.adapter.PostsItemAdapter
import com.example.melongarden.bean.PostBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val data = arrayListOf<PostBean>()
    private val adapter = PostsItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initRecycleView()
    }

    private fun initRecycleView() {
        postsRecycleView.adapter = adapter
        postsRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private fun initData() {
        for (i in 0..30) {
            data.add(PostBean("lwt"))
        }
        adapter.setData(data)
    }
}