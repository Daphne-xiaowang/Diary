package com.example.vocabularybook.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularybook.R
import com.example.vocabularybook.model.listItem

class MyAdapter(val itemList: ArrayList<listItem>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    lateinit var parent: ViewGroup
    lateinit var mOnItemClickLitener: OnItemClickLitener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        this.parent = parent
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val listItem = itemList.get(position)
        holder.english.text = listItem.english
        holder.time.text = listItem.time

        holder.itemView.setOnClickListener(View.OnClickListener() {
            mOnItemClickLitener.onItemClick(holder.itemView, position);
        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            mOnItemClickLitener.onItemLongClick(holder.itemView, position)
            true
        })
    }

    interface OnItemClickLitener {
        fun onItemClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var english: TextView
        var time: TextView

        init {
            english = itemView.findViewById(R.id.item_content)
            time = itemView.findViewById(R.id.item_time)
        }
    }

}
