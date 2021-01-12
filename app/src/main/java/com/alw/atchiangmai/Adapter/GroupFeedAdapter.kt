package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.ItemGroupFeed
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_feed.view.*




class GroupFeedAdapter(private val itemsGroup : List<ItemGroupFeed>,var onclick : onNoteCLickListener): RecyclerView.Adapter<GroupFeedAdapter.GroupFeedViewHolder>() {
    class GroupFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_feed,parent,false)
        return GroupFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupFeedViewHolder, position: Int) {

        val currency = itemsGroup[position].listItemFeed
        val itemListAdapter = FeedRecyclerAdapter(currency,onclick)
        holder.itemView.textViewTitleFeed.text = itemsGroup[position].headTitle

        holder.itemView.feedRecyclerView.setHasFixedSize(true)
        holder.itemView.feedRecyclerView.adapter = itemListAdapter


    }

    override fun getItemCount(): Int {
        return itemsGroup.size
    }


}
