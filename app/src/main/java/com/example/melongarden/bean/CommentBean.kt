package com.example.melongarden.bean

data class CommentBean(
    val comment_count: Int,
    val comments: List<Comment>,
    val user_count: Int
)

data class Comment(
    val content: String,
    val id: Int,
    val is_edited: Boolean,
    val post_id: Int,
    val time: String,
    val user_id: String,
    val vote_down: Int,
    val vote_status: Int,
    val vote_up: Int
)