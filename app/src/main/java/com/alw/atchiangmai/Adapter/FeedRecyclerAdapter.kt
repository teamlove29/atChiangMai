package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.R
import com.alw.atchiangmai.Model.ItemDataFeed
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.feed_list.view.*


interface onNoteCLickListener {
    fun onClick(postion: Int)
}

class FeedRecyclerAdapter(var itemlists : ArrayList<ItemDataFeed>,var onNoteCLickListener:onNoteCLickListener?):RecyclerView.Adapter<FeedRecyclerAdapter.FeedViewHolder>() {
    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        var image = itemView.imageViewFeed
//        var title = itemView.textViewFeedTitle.text
//        var description = itemView.textViewFeedDescription.text

        fun setOnCLick(action: onNoteCLickListener) {
            itemView.setOnClickListener {
                action.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.feed_list,parent,false)
        return  FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        var currency = itemlists[position]

        Picasso.get().load(currency.image).into(holder.itemView.imageViewFeed);
        holder.itemView.textViewFeedTitle.text = currency.title
        holder.itemView.textViewFeedDescription.text = currency.description
        onNoteCLickListener?.let { holder.setOnCLick(it) }
//        holder.bind(currency)
    }

    override fun getItemCount(): Int {
        return itemlists.size
    }



    }

