package com.example.melongarden.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.melongarden.R
import com.example.melongarden.bean.Post
import kotlinx.android.synthetic.main.post_item_binder_layout.view.*

class PostsItemAdapter : RecyclerView.Adapter<PostsItemAdapter.ItemHolder>() {

    private var data = arrayListOf<Post>()

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item_binder_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.authorName.text = data[position].title
    }

    fun setData(data: ArrayList<Post>) {
        this.data = data
    }

}