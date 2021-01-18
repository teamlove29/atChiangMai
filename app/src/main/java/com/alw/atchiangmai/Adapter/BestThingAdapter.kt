package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.ModelCardPicText1
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.best_card.view.*

class BestThingAdapter(private val arrayList: ArrayList<ModelCardPicText1>):RecyclerView.Adapter<BestThingAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bindCard(modelCardPicText1: ModelCardPicText1){
            if (modelCardPicText1.image.toString() !== "") {
                Picasso.get().load(modelCardPicText1.image)
                        .into(itemView.imageCard)
            }
            itemView.textCard.text = modelCardPicText1.txt

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCard(arrayList[position])
    }

    override fun getItemCount(): Int {
        return  arrayList.size
    }
}