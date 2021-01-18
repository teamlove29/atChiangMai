package com.alw.atchiangmai

import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import kotlinx.android.synthetic.main.activity_otop.*

class OTOPActivity : AppCompatActivity(), CategoriesOTOPAdapter.OnItemCategoryClickListener {

    /// Firebase Firestore
    val TAG = "MyMessage"

    private var otopLists = arrayListOf<OTOP_Model>()


    //Array OTOP Category IMG
    private val categoryOTOPimg = arrayOf(R.drawable.ic_otop_food, R.drawable.ic_otop_drink, R.drawable.ic_otop_tshirt, R.drawable.ic_otop_accesory)

    //Array OTOP Category IMG
    private val categoryOTOPname = arrayOf("Food", "Drink", "Clothes", "Accessories")

    // Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otop)


        ///////////////////////// Action Bar Start ////////////////////////////
        //Action Bar - OTOP

        //Set ACtion Title
//        actionbarOTOP!!.title = "OTOP"

        //Set back Button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /////////////////// OTOP Categories /////////////////////
        val categoryList = ArrayList<OTOP_Category_Model>()
        for (categoryItemList in categoryOTOPname.indices) {

            categoryList.add(OTOP_Category_Model(  categoryOTOPimg[categoryItemList], categoryOTOPname[categoryItemList] ))

        }

        rvOTOP_categories.adapter = CategoriesOTOPAdapter(categoryList, this)
        rvOTOP_categories.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    /// Get data once from Firestore
    private fun firebaseFirestore(collection : String) {
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e(TAG, "${document.id} => ${document.data}")
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
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    override fun onStart() {
        super.onStart()
//        otop_Adapter!!.startListening()

//        updateUI(currentUser)
    }

    ////// Action bar function
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu_otop, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


//    OTOP Categories Function
    override fun onItemClick(item: OTOP_Category_Model, position: Int){
       // Toast.makeText(this, item.cateOTText, Toast.LENGTH_SHORT).show()
        otopLists.clear()
        when(item.cateOTText){
           "Food" -> firebaseFirestore("otopFood")
           "Drink" -> firebaseFirestore("drink")
           "Clothes" -> firebaseFirestore("shirt")
           "Accessories" -> firebaseFirestore("acessories")
        }
    }

}