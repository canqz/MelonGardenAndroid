package com.example.melongarden.service


import com.example.melongarden.bean.CommentBean
import com.example.melongarden.bean.PostBean
import io.reactivex.Observable
import retrofit2.http.GET

interface PostService {
    @GET("api/v1/posts?page=1&page_size=20&image_mode=false")
    fun getPosts(): Observable<PostBean>

    @GET("api/v1/comments/86?page=1&page_size=20")
    fun getComment(): Observable<CommentBean>
}