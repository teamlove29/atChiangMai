package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.RestaurantData
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list.view.*

class RestaurantAdapter(var restaurantList : ArrayList<RestaurantData>, var onCLickAdapterListener : onCLickAdapterListener) : RecyclerView.Adapter<RestaurantAdapter.FoodViewHolder>(){
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.resNameTextView.text
        var rating = itemView.ratingTextView.text
        var image = itemView.resImageView

        fun bind(itemList : RestaurantData,action: onCLickAdapterListener){
            Picasso.get().load(itemList.image).into(image)
            name = itemList.name
            rating = itemList.name
            itemView.setOnClickListener {
                action.onClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currency = restaurantList[position]


        holder.itemView.resNameTextView.text = currency.name
        holder.itemView.starTextView.text = currency.rating
        holder.bind(currency,onCLickAdapterListener)

    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}