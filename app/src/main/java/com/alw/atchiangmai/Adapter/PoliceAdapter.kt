package com.alw.atchiangmai.Adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.PoliceModel
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_police_department_lists.view.*
import org.w3c.dom.Text

class PoliceAdapter(private var context: Context, private val itemPoliceList: ArrayList<PoliceModel>, private var listenerPolice: (PoliceModel) -> Unit): RecyclerView.Adapter<PoliceAdapter.PoliceViewHolder>() {

    inner class PoliceViewHolder(itemPoliceView: View): RecyclerView.ViewHolder(itemPoliceView) {
        val pd_img: ImageView = itemPoliceView.imgPoliceItem
        val pd_Name: TextView = itemPoliceView.tvPolice_name
//        val pd_des: TextView = itemPoliceView.tvPolice_des
        val pd_address: TextView = itemPoliceView.tvPolice_address
        val pd_tel: TextView = itemPoliceView.tvPolice_tel

        fun bindViewPolice(policeIMG: PoliceModel, liatenerPol: (PoliceModel) -> Unit){
            Picasso.get().load(policeIMG.cmpdImg).into(pd_img)
            pd_Name.text = policeIMG.cmpdName
//            pd_des.text = policeIMG.cmpdDes
            pd_address.text = policeIMG.cmpdAddress
            pd_tel.text = policeIMG.cmpdTel

            itemView.setOnClickListener{
                listenerPolice(policeIMG)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PoliceAdapter.PoliceViewHolder {
        return PoliceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_police_department_lists, parent, false))
    }

    override fun onBindViewHolder(holder:PoliceViewHolder, position: Int) {
        holder.bindViewPolice(itemPoliceList[position], listenerPolice)
    }

    override fun getItemCount(): Int = itemPoliceList.size
}