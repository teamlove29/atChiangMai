package com.alw.atchiangmai.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.alw.atchiangmai.R

class OTOPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otop)

        //Action Bar - OTOP
        val actionbarOTOP = supportActionBar

        //Set ACtion Title
        actionbarOTOP!!.title = "OTOP"

        //Set back Button
        actionbarOTOP.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//           // R.id.icBack -> Toast.makeText(this, "Get Back Fuck", Toast.LENGTH_SHORT).show()
//            R.id.icCart -> Toast.makeText(this, "Get Go Cart Fuck", Toast.LENGTH_SHORT).show()
//        }
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}