package com.alw.atchiangmai.Adapter

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.OTOP_Model
import com.alw.atchiangmai.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_otop_lists.view.*

class OTOP_Adapter(var itemList: ArrayList<OTOP_Model> ) :  RecyclerView.Adapter<OTOP_Adapter.OTOPViewHolder>() {

    class OTOPViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ot_img: ImageView = itemView.imgOTOPItem
        val ot_text: TextView = itemView.tvOTOP_name

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OTOPViewHolder {
        return OTOPViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_otop_lists, parent, false))
    }

    override fun onBindViewHolder(holder: OTOPViewHolder, position: Int) {
        val currently = itemList[position]
        Picasso.get().load(currently.otopImage).into(holder.ot_img)
        holder.ot_text.text = currently.otopItemText
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}




//class OTOP_Adapter(
//    private val context: Context,
//    private val imagesOT : List<OTOP_Model>) : RecyclerView.Adapter<OTOP_Adapter.OTOPViewHolder>() {
//    inner class OTOPViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val ot_img: ImageView = itemView.findViewById(R.id.imgOTOPItem)
//        val ot_text: TextView = itemView.findViewById(R.id.tvOTOP_name)
//
//        fun bindView(ot_image: OTOP_Model){
//            ot_img.setImageResource(ot_image.otopImage)
//            ot_text.text = ot_image.otopItemText
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OTOPViewHolder =
//        OTOPViewHolder(LayoutInflater.from(context).inflate(R.layout.item_otop_lists, parent, false))
//
//
//    override fun onBindViewHolder(holder: OTOPViewHolder, position: Int) {
//        holder.bindView(imagesOT[position])
//    }
//
//    override fun getItemCount(): Int = imagesOT.size
//}