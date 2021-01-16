package com.alw.atchiangmai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Adapter.CategoriesOTOPAdapter
import com.alw.atchiangmai.Adapter.OTOP_Adapter
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.OTOP_Category_Model
import com.alw.atchiangmai.Model.OTOP_Model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_otop.*

class OTOPActivity : AppCompatActivity() {

    var otopLists = arrayListOf<OTOP_Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otop)

//        setUpOTOP_RecyclerView()

        ///////////////////////// Action Bar Start ////////////////////////////
        //Action Bar - OTOP
//        val actionbarOTOP = supportActionBar
//
//        //Set ACtion Title
//        actionbarOTOP!!.title = "OTOP"
//
//        //Set back Button
//        actionbarOTOP.setDisplayHomeAsUpEnabled(true)
        /////////////////// OTOP Categories /////////////////////

        val categoryList = ArrayList<OTOP_Category_Model>()
        categoryList.add(OTOP_Category_Model(R.drawable.ic_otop_food,"Food"))
        categoryList.add(OTOP_Category_Model(R.drawable.ic_otop_drink,"Drink"))
        categoryList.add(OTOP_Category_Model(R.drawable.ic_otop_food,"Shirts"))
        categoryList.add(OTOP_Category_Model(R.drawable.ic_otop_drink,"Accessories"))

        val adapter = CategoriesOTOPAdapter(categoryList)
        rvOTOP_categories.adapter = adapter
        rvOTOP_categories.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        for (dt in categoryList){
            when(dt.cateOTText){
                "Food" -> firebaseFirestore("otopFood")
                "Drink" -> firebaseFirestore("drink")
                "Shirts" -> firebaseFirestore("shirt")
                "Accessories" -> firebaseFirestore("acessories")
            }
        }
    }


    /// Get data once from Firestore
    private fun firebaseFirestore(collection : String) {
        db.collection(collection)
            .get()
            .addOnCompleteListener { result ->
                for (document in result.result!!) {
                    Log.e("TAG", "${document.id} => ${document.data}")
                    val otopImages = document.getString("image")
                    val otopText = document.getString("name")
//                    when (collection) {
                     //   "otopFood" -> otopLists.add(OTOP_Model("$otopImages", "$otopText").toString())
                    otopLists.add(OTOP_Model("$otopImages", "$otopText"))
//                    }
                }
                rvOTOP_Lists.adapter = OTOP_Adapter(otopLists)
                rvOTOP_Lists.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

//    OTOP Categories Function
    fun showOTOPitem(view: View) {
        println("${view.tag}")

        otopLists.clear()

        when(view.tag){
           "FoodOtop" -> firebaseFirestore("otopFood")
           "DrinkOtop" -> firebaseFirestore("drink")
           "ShirtsOtop" -> firebaseFirestore("shirt")
           "AccessoriesOtop" -> firebaseFirestore("acessories")
        }
    }
}
