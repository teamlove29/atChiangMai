package com.alw.atchiangmai.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hospital_detail.*
import org.w3c.dom.Text

class HospitalDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_detail)

        val imageIntent = intent.getParcelableExtra<Hospital_Model>(HospitalActivity.INTENT_PARCELABLE_hospital)

        val imgSrcHos = findViewById<ImageView>(R.id.imgHosDetail)
        val imgHosName = findViewById<TextView>(R.id.txtNameHosDetail)
        val imgHosDes = findViewById<TextView>(R.id.txtDesHosDetail)
        val imgHosAddress = findViewById<TextView>(R.id.addressHosDetail)
        val imgHosTel = findViewById<TextView>(R.id.telHosDetail)

        if (imageIntent != null) {
            Picasso.get().load(imageIntent.hospitalImg).into(imgSrcHos)
        }
//        imgSrcHos.setImageResource(imageIntent)
        imgHosName.text = imageIntent!!.hospitalName
        imgHosDes.text = imageIntent.hospitalDes
        imgHosAddress.text = imageIntent.hospitalAddress
        imgHosTel.text = imageIntent.hospitalTel
    }

}