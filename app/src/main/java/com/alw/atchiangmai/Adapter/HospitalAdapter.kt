package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hospital_lists.view.*

class HospitalAdapter(private var itemHospitalList: ArrayList<Hospital_Model>): RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {
    inner class HospitalViewHolder(itemHosView: View): RecyclerView.ViewHolder(itemHosView){
        val hospital_img: ImageView = itemHosView.imgHospitalItem
        val hospital_name: TextView = itemHosView.tvHospital_name
        val hospital_description: TextView = itemHosView.tvHospital_des
        val hospital_address: TextView = itemHosView.tvHospital_address
        val hospital_tel: TextView = itemHosView.tvHospital_tel

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        return HospitalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hospital_lists, parent, false))
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val itemHosCurrent = itemHospitalList[position]
        Picasso.get().load(itemHosCurrent.hospitalImg).into(holder.hospital_img)
        holder.hospital_name.text = itemHosCurrent.hospitalName
        holder.hospital_description.text = itemHosCurrent.hospitalDes
        holder.hospital_address.text = itemHosCurrent.hospitalAddress
        holder.hospital_tel.text = itemHosCurrent.hospitalTel
    }

    override fun getItemCount(): Int = itemHospitalList.size
}