package com.alw.atchiangmai.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.R
import com.alw.atchiangmai.data.OTOPdata
import kotlinx.android.synthetic.main.item_otop_lists.view.*

class OTOP_Adapter(
        private val context: Context,
    private val imagesOT : List<OTOPdata>) : RecyclerView.Adapter<OTOP_Adapter.OTOPViewHolder>() {
    inner class OTOPViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ot_img: ImageView = itemView.findViewById(R.id.imgOTOPItem)
        val ot_text: TextView = itemView.findViewById(R.id.tvOTOP_name)

        fun bindView(ot_image: OTOPdata){
            ot_img.setImageResource(ot_image.otopImage)
            ot_text.text = ot_image.otopItemText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OTOPViewHolder =
            OTOPViewHolder(LayoutInflater.from(context).inflate(R.layout.item_otop_lists, parent, false))


    override fun onBindViewHolder(holder: OTOPViewHolder, position: Int) {
        holder.bindView(imagesOT[position])
    }

    override fun getItemCount(): Int = imagesOT.size
}