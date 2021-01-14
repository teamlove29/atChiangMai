package com.alw.atchiangmai

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.OTOP_Adapter
import com.alw.atchiangmai.Model.OTOP_Model
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_otop.*

class OTOPActivity : AppCompatActivity() {

    /// Firebase Firestore
    val TAG = "MyMessage"
    private val db:FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = db.collection("otopFood")
    ////

    var otop_Adapter: OTOP_Adapter? = null
    var otopLists = arrayListOf<OTOP_Model>()

    // ...
    // Initialize Firebase Auth
    var oAuth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otop)

//        setUpOTOP_RecyclerView()

        ///// Fire base zone //////
        oAuth = FirebaseAuth.getInstance()
        val email = "nk.tae26986@gmail.com"
        val password = "123456tae"
        oAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "signIn: Success!")
                    // update UI with the signed-in user's information
//                        val user = mAuth!!.getCurrentUser()
//                        updateUI(user)
                } else {
                    Log.w(TAG, "signIn: Fail!", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                }
            }

        ///////////////////////// Action Bar Start ////////////////////////////
        //Action Bar - OTOP
        val actionbarOTOP = supportActionBar

        //Set ACtion Title
        actionbarOTOP!!.title = "OTOP"

        //Set back Button
        actionbarOTOP.setDisplayHomeAsUpEnabled(true)
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

        val currentUser = oAuth.currentUser
//        updateUI(currentUser)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        otop_Adapter!!.stopListening()
//    }


    ////// Action bar function

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









// Old version
//////////////////////// Array OTOP Data ////////////////////////////

//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_otop)
//
//    val itemsOTP = listOf<OTOP_Model>(
//        OTOP_Model(R.drawable.minato_namikaze, "otop 1"),
//        OTOP_Model(R.drawable.naruto, "otop 2"),
//        OTOP_Model(R.drawable.sasuke_part_1, "otop 3")
//    )
//
////////////////////////// Adapter Start ////////////////////////////
//
//    val recOTOPlist = findViewById<RecyclerView>(R.id.rvOTOP_Lists)
//    recOTOPlist.layoutManager = LinearLayoutManager(this)
//
//    recOTOPlist.setHasFixedSize(true)
//    recOTOPlist.adapter = OTOP_Adapter(this, itemsOTP)
//
/////////////////////////// Action Bar Start ////////////////////////////
//    //Action Bar - OTOP
//    val actionbarOTOP = supportActionBar
//
//    //Set ACtion Title
//    actionbarOTOP!!.title = "OTOP"
//
//    //Set back Button
//    actionbarOTOP.setDisplayHomeAsUpEnabled(true)
//
//}
//
//override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//    menuInflater.inflate(R.menu.app_bar_menu, menu)
//    return true
//}
//
////    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        when(item.itemId){
////           // R.id.icBack -> Toast.makeText(this, "Get Back Fuck", Toast.LENGTH_SHORT).show()
////            R.id.icCart -> Toast.makeText(this, "Get Go Cart Fuck", Toast.LENGTH_SHORT).show()
////        }
////        return true
////    }
//
//override fun onSupportNavigateUp(): Boolean {
//    onBackPressed()
//    return true
//}