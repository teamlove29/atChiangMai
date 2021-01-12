package com.alw.atchiangmai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alw.atchiangmai.Adapter.ViewPagerAdapter
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkingUser()
        insertSlideImg()
    }

    private fun insertSlideImg(){

        val ref = db.collection("slider")
        ref.get().addOnCompleteListener {
            val arrayList = ArrayList<String>()
            if (it.isSuccessful){
                for (doc in it.result!!){
                    arrayList.add(doc["image"].toString())
                }
            }
            val adapter = ViewPagerAdapter(this, arrayList)
            view_pager.adapter = adapter
         }
    }

    private fun checkingUser(){
       if(FirebaseAuth.getInstance().currentUser?.uid == null) {
           val inten =Intent(this, UserActivity::class.java)
           startActivity(inten)

       }else {
           txt_user_name.text = "${FirebaseAuth.getInstance().currentUser?.displayName}"


           if (FirebaseAuth.getInstance().currentUser?.photoUrl !== null) {
               Picasso.get().load(FirebaseAuth.getInstance().currentUser?.photoUrl)
                   .into(image_user)

           } else {
               image_user.setImageResource(R.drawable.baseline_account_circle_black_24dp)
           }
       }
    }

}