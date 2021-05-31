package com.example.melongarden.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.melongarden.R
import com.example.melongarden.bean.CommentBean
import kotlinx.android.synthetic.main.comment_item_binder_layout.view.*

class CommentItemAdapter : RecyclerView.Adapter<CommentItemAdapter.ItemHolder>() {

    private var commentBean: CommentBean? = null

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item_binder_layout, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.contentTv.text = commentBean?.comments?.get(position)?.content ?: "Hello"
    }

    override fun getItemCount(): Int = commentBean?.comments?.size ?: 0

    fun setData(commentBean:CommentBean){
        this.commentBean = commentBean
    }
}