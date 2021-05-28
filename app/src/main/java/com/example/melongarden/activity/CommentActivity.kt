package com.example.melongarden.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.melongarden.Helper
import com.example.melongarden.R
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        postId.text = intent.getStringExtra(Helper.POST_ID)
    }
}