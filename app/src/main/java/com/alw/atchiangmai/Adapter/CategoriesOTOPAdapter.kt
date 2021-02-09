package com.alw.atchiangmai.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.R
import com.alw.atchiangmai.Model.OTOP_Category_Model
import com.alw.atchiangmai.Model.OTOP_Model
import kotlinx.android.synthetic.main.category_ot_row.view.*

class CategoriesOTOPAdapter(private val categoryList: ArrayList<OTOP_Category_Model>,
                            var clickListener: OnItemCategoryClickListener) : RecyclerView.Adapter<CategoriesOTOPAdapter.CateOTOPViewHolder>() {

    override fun getItemCount(): Int = categoryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateOTOPViewHolder {
        return CateOTOPViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_ot_row, parent, false))

    }

    override fun onBindViewHolder(holder: CateOTOPViewHolder, position: Int) {
//        val categoryPos = categoryList[position]
//        holder.cateOTimg.setImageResource(categoryPos.cateOTimg)
//        holder.cateOTtext.text = categoryPos.cateOTText
        holder.initializeClickBind(categoryList[position], clickListener)

    }

    inner class CateOTOPViewHolder(itemCateOTOPView: View) : RecyclerView.ViewHolder(itemCateOTOPView){
        val cateOTimg: ImageView = itemCateOTOPView.categoryIMG
        val cateOTtext: TextView = itemCateOTOPView.tvCateOTOP

        @SuppressLint("SetJavaScriptEnabled")

        fun initializeClickBind(item: OTOP_Category_Model, action: OnItemCategoryClickListener) {
            try {
                cateOTimg.setImageResource(item.cateOTimg)
                cateOTtext.text = item.cateOTText
                itemView.setOnClickListener{
                    action.onItemClick(item, adapterPosition)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }


        }
    }

    interface OnItemCategoryClickListener{
        fun onItemClick(item: OTOP_Category_Model, position: Int)
    }

}
