package com.alw.atchiangmai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alw.atchiangmai.Model.ItemDataFeed
import com.alw.atchiangmai.Model.ItemGroupFeed
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        }
}