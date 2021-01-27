package com.alw.atchiangmai.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.HospitalAdapter
import com.alw.atchiangmai.Adapter.PoliceAdapter
import com.alw.atchiangmai.FirebaseController
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.Model.PoliceModel
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_hospital.*
import kotlinx.android.synthetic.main.activity_police.*

class PoliceActivity : AppCompatActivity() {

    private var TAG = "My Police Station Tag"
    private var policeLists = ArrayList<PoliceModel>()

    companion object{
        val INTENT_PARCELABLE_Police = "OBJECT_INTENT"
    }


    override fun onStart(){
        super.onStart()
        getFirestorePoliceStationResult()
    }

    override fun onResume() {
        super.onResume()
        searchViewPoliceDepartment.clearFocus()
        searchViewPoliceDepartment.setQuery("", false)
        rvPolice_Lists.adapter?.notifyDataSetChanged()
        policeLists.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_police)

        shimmerLayoutPolice1.startShimmerAnimation()
        Handler().postDelayed({
            shimmerLayoutPolice1.stopShimmerAnimation()
            shimmerLayoutPolice1.visibility = View.GONE
        }, 4000)

        searchViewPoliceDepartment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryPolice: String?): Boolean {
                if(queryPolice != null){
                    policeLists.clear()
                    searchPoliceStationInFirestore(queryPolice)
                }
                else{
                    getFirestorePoliceStationResult()
                }
                return false
            }

            override fun onQueryTextChange(newTextPoliceStation: String?): Boolean {
                if (newTextPoliceStation != null){
                    policeLists.clear()
                    searchPoliceStationInFirestore(newTextPoliceStation)
                }
                else {
                    getFirestorePoliceStationResult()
                }
                return false
            }
        })

//        val collectionPoliceFirestore = "policestation"
//        getFirestorePoliceStationResult(collectionPoliceFirestore)
    }

    //Get data once from Firestore
    private fun getFirestorePoliceStationResult(){
        FirebaseController.Firebase.db.collection("policestation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e(TAG, "${document.id} => ${document.data}")
                    val policeImages = document.getString("image")
                    val policeName = document.getString("name")
                    val policeDescription = document.getString("des")
                    val policeAddress = document.getString("add")
                    val policeTel = document.getString("tel")
//                    when (collection) {
//                        //   "otopFood" -> otopLists.add(OTOP_Model("$otopImages", "$otopText").toString())
//                        "policestation" -> policeLists.add(
//                            PoliceModel("$policeImages", "$policeName", "$policeAddress", "$policeTel")
//                        )
//                    }
                    policeLists.add(
                            PoliceModel("$policeImages",
                                "$policeName",
                                "$policeAddress",
                                "$policeTel"
                            )
                    )
                }
                rvPolice_Lists.layoutManager = LinearLayoutManager(this)
                rvPolice_Lists.setHasFixedSize(true)
                rvPolice_Lists.adapter = PoliceAdapter(this, policeLists){
                    //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, PoliceStationDetailActivity::class.java)
                    intent.putExtra(PoliceActivity.INTENT_PARCELABLE_Police, it)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }

    private fun searchPoliceStationInFirestore(searchPolTxt: String){
        FirebaseController.Firebase.db.collection("policestation")
            .whereEqualTo("name", searchPolTxt)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (documentSearch in task.result!!) {
                        Log.e(TAG, "${documentSearch.id} => ${documentSearch.data}")
                        val policeImages = documentSearch.getString("image")
                        val policeName = documentSearch.getString("name")
                        val policeDescription = documentSearch.getString("des")
                        val policeAddress = documentSearch.getString("add")
                        val policeTel = documentSearch.getString("tel")

                        policeLists.add(
                            PoliceModel(
                                "$policeImages",
                                "$policeName",
                                "$policeAddress",
                                "$policeTel"
                            )
                        )
                    }
                }
                rvPolice_Lists.layoutManager = LinearLayoutManager(this)
                rvPolice_Lists.setHasFixedSize(true)
                rvPolice_Lists.adapter = PoliceAdapter(this, policeLists) {
                    //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, PoliceStationDetailActivity::class.java)
                    intent.putExtra(PoliceActivity.INTENT_PARCELABLE_Police, it)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                }
            }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
            }

    }
}