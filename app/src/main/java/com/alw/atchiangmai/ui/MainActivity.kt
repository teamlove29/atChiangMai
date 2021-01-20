package com.alw.atchiangmai.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alw.atchiangmai.Adapter.BestThingAdapter
import com.alw.atchiangmai.Adapter.VdoAdapter
import com.alw.atchiangmai.Adapter.ViewPagerAdapter
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.ModelCardPicText1
import com.alw.atchiangmai.Model.ModelYoutube
import com.alw.atchiangmai.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkingUser()
        insertSlideImg()
        getBestThings()
        vdoList()
        btn_more.setOnClickListener {
            val intent = Intent(this, MoreActivity::class.java)
            startActivity(intent)
        }
        btn_exchange.setOnClickListener {
            val intent = Intent(this, ExchangeActivity::class.java)
            startActivity(intent)
        }
        btn_otop.setOnClickListener{
            val intent = Intent(this, OTOPActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
        }
        btn_feed.setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
        }
        btn_hotel.setOnClickListener {
            val intent = Intent(this, HotelActivity::class.java)
            startActivity(intent)
        }
        btn_hospital.setOnClickListener {
            val intent = Intent(this, HospitalActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
        }
        btn_police.setOnClickListener {
            val intent = Intent(this, PoliceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
        }
    }

    private fun insertSlideImg() {

        val ref = db.collection("slider")
        ref.get().addOnCompleteListener {
            val arrayList = ArrayList<String>()
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    arrayList.add(doc["image"].toString())
                }
            }
            val adapter = ViewPagerAdapter(this, arrayList)
            view_pager.adapter = adapter
        }
    }

    private fun checkingUser() {
        if (FirebaseAuth.getInstance().currentUser?.uid == null) {
            val inten = Intent(this, UserActivity::class.java)
            startActivity(inten)

        } else {
            txt_user_name.text = "${FirebaseAuth.getInstance().currentUser?.displayName}"


            if (FirebaseAuth.getInstance().currentUser?.photoUrl !== null) {
                Picasso.get().load(FirebaseAuth.getInstance().currentUser?.photoUrl)
                    .into(image_user)

            } else {
                image_user.setImageResource(R.drawable.baseline_account_circle_black_24dp)
            }
        }
    }

    private fun getBestThings() {
        val ref = db.collection("activity")
        ref.get().addOnCompleteListener {
            val arrayList = ArrayList<ModelCardPicText1>()
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    val txt: String = doc["name"].toString()
                    val uri = Uri.parse(doc.get("image").toString())
                    arrayList.add(ModelCardPicText1(uri, txt,""))
                }
            }
            val adapter = BestThingAdapter(arrayList)
            rcv_best_things.layoutManager = GridLayoutManager(
                this,
                1,
                GridLayoutManager.HORIZONTAL,
                false
            )
            rcv_best_things.adapter = adapter
            adapter.notifyDataSetChanged()


        }



    }

    private fun vdoList() {
        val ref = db.collection("vdo")
        ref.get().addOnCompleteListener {
            val arrayListVdo = ArrayList<ModelYoutube>()
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    val txt: String = doc["title"].toString()
                    val id = getVdoID(doc.get("url").toString())
                    arrayListVdo.add(ModelYoutube(id, txt))
                }
            }
            val adapterVdo = VdoAdapter(arrayListVdo)
            rcv_vdo.layoutManager = GridLayoutManager(
                this,
                1,
                GridLayoutManager.HORIZONTAL,
                false
            )
            rcv_vdo.adapter = adapterVdo
            adapterVdo.notifyDataSetChanged()
        }
    }

    private fun getVdoID(url: String): String {
        return url.substringAfter("watch?v=").substringBefore("&")
    }

}
