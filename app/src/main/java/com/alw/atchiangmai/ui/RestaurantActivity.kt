package com.alw.atchiangmai.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.RestaurantAdapter
import com.alw.atchiangmai.Adapter.onCLickAdapterListener
import com.alw.atchiangmai.FirebaseController
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.ItemDataFeed
import com.alw.atchiangmai.Model.RestaurantData
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity(), onCLickAdapterListener {
    val foodList = ArrayList<RestaurantData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        db.collection("restaurant")
            .get().addOnCompleteListener {
                if (it.isSuccessful){
                    for(document in it.result!!){
                        val image = document.getString("image")
                        val title = document.getString("name")
                        val description = document.getString("des")
                        val rating = document.get("rating").toString()
                        val telephone = document.getString("tel")
                        val map = document.getString("map")


                        foodList.add(RestaurantData("$image","$title", rating,"$description", "$telephone", "$map"))
                    }
                    restaurantRecyclerView.adapter = RestaurantAdapter(foodList,this)
                    restaurantRecyclerView.layoutManager = LinearLayoutManager(this)
                }
            }
    }

    override fun onClick(postion: Int) {
        val intent = Intent(this,DetailRestaurantActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("restaurant",foodList[postion])
        intent.putExtras(bundle)
        startActivity(intent)
    }
}