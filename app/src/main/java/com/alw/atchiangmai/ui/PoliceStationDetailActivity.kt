package com.alw.atchiangmai.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.Model.PoliceModel
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso

class PoliceStationDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_police_station_detail)

        val imagePoliceIntent = intent.getParcelableExtra<PoliceModel>(PoliceActivity.INTENT_PARCELABLE_Police)

        val imgSrcPol = findViewById<ImageView>(R.id.imgPolDetail)
        val imgPolName = findViewById<TextView>(R.id.txtNamePolDetail)
        val imgPolAddress = findViewById<TextView>(R.id.addressPolDetail)
        val imgPolTel = findViewById<TextView>(R.id.telPolDetail)


        if (imagePoliceIntent != null) {
            Picasso.get().load(imagePoliceIntent.cmpdImg).into(imgSrcPol)
        }
//        imgSrcHos.setImageResource(imageIntent)
        imgPolName.text = imagePoliceIntent!!.cmpdName
//        imgPolDes.text = imagePoliceIntent.cmpdDes
        imgPolAddress.text = imagePoliceIntent.cmpdAddress
        imgPolTel.text = imagePoliceIntent.cmpdTel
    }
}