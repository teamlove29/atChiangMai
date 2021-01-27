package com.alw.atchiangmai.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alw.atchiangmai.Model.RestaurantData
import com.alw.atchiangmai.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_restaurant.*

class DetailRestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_restaurant)

        val intent = intent
        val restaurantData = intent.getParcelableExtra<RestaurantData>("restaurant")
        Picasso.get().load(restaurantData!!.image).into(DetailResImageView)
        titleDetailResTextView.text = restaurantData.name
        descriptionResTextView.text = restaurantData.description



    }
}