package com.alw.atchiangmai.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hospital_lists.view.*
import kotlinx.android.synthetic.main.item_hospital_lists.view.imgHospitalItem
import kotlinx.android.synthetic.main.item_hospital_lists.view.tvHospital_address
import kotlinx.android.synthetic.main.item_hospital_lists.view.tvHospital_des
import kotlinx.android.synthetic.main.item_hospital_lists.view.tvHospital_name
import kotlinx.android.synthetic.main.item_hospital_lists.view.tvHospital_tel

class HospitalAdapter(private var context: Context,
                      private var itemHospitalList: ArrayList<Hospital_Model>,
                      private val listenerHospital: (Hospital_Model) -> Unit): RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    val ITEM_HOSPITAL_TYPE = 0
    val LOADING_HOSPITAL_TYPE = 1


    inner class HospitalViewHolder(itemHosView: View): RecyclerView.ViewHolder(itemHosView){
        //val shimmerFrameLayout: ShimmerFrameLayout = itemHosView.shimmerLayoutHos1
        private val hospital_img: ImageView = itemHosView.imgHospitalItem
        private val hospital_name: TextView = itemHosView.tvHospital_name
        private val hospital_description: TextView = itemHosView.tvHospital_des
        private val hospital_address: TextView = itemHosView.tvHospital_address
        private val hospital_tel: TextView = itemHosView.tvHospital_tel

        fun bindVIew(hosIMG: Hospital_Model, listener: (Hospital_Model)->Unit){

            Picasso.get().load(hosIMG.hospitalImg).into(hospital_img)
         //  hospital_img.setImageResource(hosIMG.hospitalImg)
            hospital_name.text = hosIMG.hospitalName
            hospital_description.text = hosIMG.hospitalDes
            hospital_address.text = hosIMG.hospitalName
            hospital_tel.text = hosIMG.hospitalTel

            itemView.setOnClickListener{
                listener(hosIMG)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        return if(viewType == ITEM_HOSPITAL_TYPE){
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_hospital_lists, parent, false)
            HospitalViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
            HospitalViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        holder.bindVIew(itemHospitalList[position], listenerHospital)

//        val itemHosCurrent = itemHospitalList[position]
//        Picasso.get().load(itemHosCurrent.hospitalImg).into(holder.hospital_img)
//        holder.hospital_name.text = itemHosCurrent.hospitalName
//        holder.hospital_description.text = itemHosCurrent.hospitalDes
//        holder.hospital_address.text = itemHosCurrent.hospitalAddress
//        holder.hospital_tel.text = itemHosCurrent.hospitalTel
    }

    override fun getItemCount(): Int {
        return itemHospitalList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemHospitalList[position].hospitalName == "loadmore_hospital"){
            LOADING_HOSPITAL_TYPE
        }else{
            ITEM_HOSPITAL_TYPE
        }
    }

}

