package com.alw.atchiangmai.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_more.*

class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        val uriTaxi =
            Uri.parse("https://www.thailandmagazine.com/wp-content/uploads/2018/03/Vervoer-in-Chiang-Mai-C-Dennis-Jarvis_1020x680.jpg")
        val uriBus =
            Uri.parse("https://www.thaiticketmajor.com/variety/img_news/title/original1/2399/11399/title_ttmnews_11399-20190514112622.jpg")
        val uriMass =
            Uri.parse("https://upload.wikimedia.org/wikipedia/commons/4/4d/Thai_Massage.jpg")
        Picasso.get().load(uriTaxi)
            .into(imgTaxi)
        Picasso.get().load(uriBus)
            .into(imgBus)
        Picasso.get().load(uriMass)
            .into(imgMassage)

        toolbarMore.setOnClickListener {
            finish()
        }
    }
}