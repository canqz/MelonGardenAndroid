package com.example.melongarden.service


import com.example.melongarden.bean.CommentBean
import com.example.melongarden.bean.PostBean
import com.example.melongarden.bean.ReplyCommentBean
import com.example.melongarden.bean.TokenBean
import io.reactivex.Observable
import retrofit2.http.*

interface PostService {
    @GET("api/v1/posts?page=1&page_size=20&image_mode=false")
    fun getPosts(): Observable<PostBean>


    @GET("api/v1/comments/{postId}?page=1&page_size=20")
    fun getComment(@Path("postId") postId: Int): Observable<CommentBean>

    @POST("/api/v1/accounts/login")
    fun login(@Query("id") id: String, @Query("password") password: String): Observable<TokenBean>

    @POST("/api/v1/comments")
    fun postComment(
        @Header("token") token: String,
        @Query("content") content: String,
        @Query("post_id") postId: Int
    ): Observable<ReplyCommentBean>
}