package com.example.melongarden.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.melongarden.R
import com.example.melongarden.bean.CommentBean
import kotlinx.android.synthetic.main.comment_item_binder_layout.view.*
import org.jsoup.Jsoup
import java.util.HashSet
import java.util.regex.Pattern

class CommentItemAdapter : RecyclerView.Adapter<CommentItemAdapter.ItemHolder>() {

    private var commentBean: CommentBean? = null
    private var context: Context? = null

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item_binder_layout, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val text = commentBean?.comments?.get(position)?.content ?: ""
        holder.itemView.userId.text = commentBean?.comments?.get(position)?.user_id ?: ""
        holder.itemView.contentTv.apply {
            if (text == "") {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                this.text = Jsoup.parse(text).text()
            }
        }

        val set = getImgStr(text)
        holder.itemView.contentImage.apply {
            if(set.isNotEmpty()){
                visibility = View.VISIBLE
                Glide.with(context).load(set.firstOrNull()).into(holder.itemView.contentImage)
            }else{
                visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = commentBean?.comments?.size ?: 0

    fun setData(commentBean: CommentBean) {
        this.commentBean = commentBean
    }

    private fun getImgStr(htmlStr: String?): Set<String> {
        val pics: MutableSet<String> = HashSet()
        var img = ""
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        val regexImg = "<img.*src\\s*=\\s*(.*?)[^>]*?>"
        val pImage = Pattern.compile(regexImg, Pattern.CASE_INSENSITIVE)
        val mImage = pImage.matcher(htmlStr)
        while (mImage.find()) {
            // 得到<img />数据
            img = mImage.group()
            // 匹配<img>中的src数据
            val m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img)
            while (m.find()) {
                pics.add(m.group(1))
            }
        }
        return pics
    }
}