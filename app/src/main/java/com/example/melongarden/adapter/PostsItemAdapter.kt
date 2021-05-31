package com.example.melongarden.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.melongarden.R
import com.example.melongarden.bean.PostBean
import kotlinx.android.synthetic.main.post_item_binder_layout.view.*

class PostsItemAdapter : RecyclerView.Adapter<PostsItemAdapter.ItemHolder>() {

    private var dataPost: PostBean? = null
    private var onItemClickListener: OnItemClickListener? = null

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item_binder_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = dataPost?.posts?.size ?: 0

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.title.text = dataPost?.posts?.get(position)?.title ?: ""
        holder.itemView.postContent.text = dataPost?.post_briefs?.get(position) ?: ""
        holder.itemView.authorName.text = dataPost?.posts?.get(position)?.user_id ?: ""
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener { view ->
                onItemClickListener!!.onItemClick(
                    view,
                    position
                )
            }
        }
    }

    fun setData(data: PostBean) {
        this.dataPost = data
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
}