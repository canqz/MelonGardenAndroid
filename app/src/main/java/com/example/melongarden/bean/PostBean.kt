package com.example.melongarden.bean

data class PostBean(
    val post_briefs: List<String>,
    val post_images: List<Any>,
    val posts: ArrayList<Post>,
    val total_posts: Int
)

data class Post(
    val comment_count: Int,
    val id: Int,
    val latest_reply: String,
    val pinned: Boolean,
    val reply_user_id: String,
    val title: String,
    val user_id: String,
    val vote_down: Int,
    val vote_status: Int,
    val vote_up: Int
)