package com.alw.atchiangmai.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.ItemDataFeed
import com.alw.atchiangmai.Model.ItemGroupFeed
import com.alw.atchiangmai.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.feed_group.view.*



class GroupFeedAdapter(private val itemsGroup : List<ItemGroupFeed>,var onclick : onCLickAdapterListener): RecyclerView.Adapter<GroupFeedAdapter.GroupFeedViewHolder>() {
    class GroupFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun setOnClick(action: onCLickAdapterListener){
            itemView.setOnClickListener {
                action.onClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_group,parent,false)
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
