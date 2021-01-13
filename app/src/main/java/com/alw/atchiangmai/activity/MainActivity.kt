package com.alw.atchiangmai.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOTOP.setOnClickListener{
            //Create instance of an intent that to start OTOP activity
            Intent(this, OTOPActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}