package com.alw.atchiangmai.activity

import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.R
import com.alw.atchiangmai.adapter.OTOP_Adapter
import com.alw.atchiangmai.data.OTOPdata
import kotlinx.android.synthetic.main.activity_otop.*

class OTOPActivity : AppCompatActivity() {

///////////////////////// Array OTOP Data ////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otop)

        val itemsOTP = listOf<OTOPdata>(
            OTOPdata(R.drawable.minato_namikaze, "otop 1"),
            OTOPdata(R.drawable.naruto, "otop 2"),
            OTOPdata(R.drawable.sasuke_part_1, "otop 3")
        )

//////////////////////// Adapter Start ////////////////////////////

        val recOTOPlist = findViewById<RecyclerView>(R.id.rvOTOP_Lists)
        recOTOPlist.layoutManager = LinearLayoutManager(this)

        recOTOPlist.setHasFixedSize(true)
        recOTOPlist.adapter = OTOP_Adapter(this, itemsOTP)

///////////////////////// Action Bar Start ////////////////////////////
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