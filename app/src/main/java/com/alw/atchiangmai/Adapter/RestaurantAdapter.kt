package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.RestaurantData
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_list.view.*

class RestaurantAdapter(var restaurantList: ArrayList<RestaurantData>, var onCLickAdapterListener: onCLickAdapterListener) : ListAdapter<RestaurantData, RestaurantAdapter.FoodViewHolder>(RestaurantDiffUtil()){
//) : RecyclerView.Adapter<RestaurantAdapter.FoodViewHolder>(){


    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.resImageView!!
        fun bind(itemList: RestaurantData, action: onCLickAdapterListener){
            Picasso.get().load(itemList.image).into(image)
            itemView.resNameTextView.text = itemList.name
            itemView.starTextView.text = itemList.rating

            itemView.setOnClickListener {
                action.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.restaurant_list,
            parent,
            false
        )
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currency = restaurantList[position]
        holder.bind(currency, onCLickAdapterListener)

    }
    override fun getItemCount(): Int {
        return restaurantList.size
    }
}


class RestaurantDiffUtil : DiffUtil.ItemCallback<RestaurantData>(){
    override fun areItemsTheSame(oldItem: RestaurantData, newItem: RestaurantData): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RestaurantData, newItem: RestaurantData): Boolean {
        return oldItem == newItem
    }
}


//class MyRestaurantDiffUtil(var oldList : ArrayList<RestaurantData>, var newList : ArrayList<RestaurantData>) : DiffUtil.Callback(){
////    ให้เรา return จำนวนของ List ตัวเดิมเข้าไป
//    override fun getOldListSize(): Int {
//        return oldList.size
//    }
////    ให้เรา return จำนวนของ List ตัวใหม่ที่มีการเปลี่ยนแปลงเข้าไป
//    override fun getNewListSize(): Int {
//        return newList.size
//    }
////    ให้เราเขียนคำสั่งที่ใช้ในการตรวจสอบข้อมูลตัวเก่า และข้อมูลตัวใหม่ว่าใช่ข้อมูลตัวเดียวกันหรือไม่ โดยจะเช็คจาก Unique Id ของข้อมูลแต่ละตัว
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//       return oldItemPosition == newItemPosition
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition] == newList[newItemPosition]
//    }
//    //    ถ้า areItemsTheSame return true และ areContentsTheSame return false DiffUtil จะเรียกใช้ method นี้ เพื่อเอาข้อมูล Payload เกี่ยวกับการเปลี่ยนแปลงต่างๆ ซึ่งข้อมูลที่ได้จากการ getChangePayload นั้นจะถูกส่งไปยัง method ด้านล่างใน Adapter ของ RecyclerView
//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        return super.getChangePayload(oldItemPosition, newItemPosition)
//    }
//
//}


