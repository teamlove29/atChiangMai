package com.alw.atchiangmai.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.ModelCardPicText1

class BestThingAdapter(val arrayList: ArrayList<ModelCardPicText1>):RecyclerView.Adapter<BestThingAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bindCard(modelCardPicText1: ModelCardPicText1){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}