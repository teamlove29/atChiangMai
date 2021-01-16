package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.R
import com.alw.atchiangmai.model.OTOP_Category_Model
import kotlinx.android.synthetic.main.category_ot_row.view.*

class CategoriesOTOPAdapter(private val categoriesOtop: ArrayList<OTOP_Category_Model>) : RecyclerView.Adapter<CategoriesOTOPAdapter.CateOTOPViewHolder>() {

    inner class CateOTOPViewHolder(itemCateOTOPView: View) : RecyclerView.ViewHolder(itemCateOTOPView){
        val cateOTtext: TextView = itemCateOTOPView.tvCateOTOP
    }

    override fun getItemCount(): Int = categoriesOtop.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateOTOPViewHolder {
        return CateOTOPViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_ot_row, parent, false))

    }

    override fun onBindViewHolder(holder: CateOTOPViewHolder, position: Int) {

        holder.cateOTtext.text = categoriesOtop[position].cateOTText

    }

}
