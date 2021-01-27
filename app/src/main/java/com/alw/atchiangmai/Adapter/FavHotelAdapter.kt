package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.ModelCardPicText1
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fav_hotel_card.view.*

class FavHotelAdapter(private val arrayList: ArrayList<ModelCardPicText1>): RecyclerView.Adapter<FavHotelAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        fun bindCard_fav(modelCardPicText1: ModelCardPicText1){
            if (modelCardPicText1.image.toString() !== "") {
                Picasso.get().load(modelCardPicText1.image)
                    .into(itemView.fav_imageCard)
            }
            itemView.fav_textCard.text = modelCardPicText1.txt
            itemView.fav_text_detail.text = modelCardPicText1.txt2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_hotel_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCard_fav(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}