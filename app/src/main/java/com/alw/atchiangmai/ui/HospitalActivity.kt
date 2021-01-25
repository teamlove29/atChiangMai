package com.alw.atchiangmai.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.HospitalAdapter
import com.alw.atchiangmai.FirebaseController
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_hospital.*
import java.util.*
import kotlin.collections.ArrayList

class HospitalActivity: AppCompatActivity() {

    private var TAG = "My Hospital Tag"

    private var hospitalList = ArrayList<Hospital_Model>()


    companion object {
        const val INTENT_PARCELABLE_hospital = "OBJECT_INTENT"
    }

    override fun onStart() {
        super.onStart()
        getFirestoreHospitalResult()
    }

    override fun onResume() {
        super.onResume()
        searchViewHospital.clearFocus()
        searchViewHospital.setQuery("",false)
//        searchViewHospital.isIconified = false;
        rvHospital_Lists.adapter?.notifyDataSetChanged()
        hospitalList.clear()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        // Firestore Collection
    //    getFirestoreHospitalResult()

        searchViewHospital.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(queryHospital: String?): Boolean {
                if (queryHospital != null){
                    hospitalList.clear()
//                    println(query)
                    searchHospitalInFirestore(queryHospital)
                }
                else {
                    getFirestoreHospitalResult()
                }
                return false
            }
            override fun onQueryTextChange(newTextHospital: String?): Boolean {
                if (newTextHospital != null){
                    hospitalList.clear()
                    searchHospitalInFirestore(newTextHospital)
                }
                else {
                    getFirestoreHospitalResult()
                }
               return false
            }
        })


    }


    private fun searchHospitalInFirestore(searchHosTxt: String){
        // Search query
    //    println("Search text: $searchText")
        FirebaseController.Firebase.db.collection("hospital")
            .whereEqualTo("name", searchHosTxt)
            .get()
            .addOnCompleteListener { task ->
             //  val hospitalList = ArrayList<Hospital_Model>()
                if (task.isSuccessful){
                    for (documentSearch in task.result!!){
                        Log.e(TAG, "${documentSearch.id} => ${documentSearch.data}")
                        val hospitalImages = documentSearch.getString("image")
                        val hospitalName = documentSearch.getString("name")
                        val hospitalDescription = documentSearch.getString("des")
                        val hospitalAddress = documentSearch.getString("add")
                        val hospitalTel = documentSearch.getString("tel")

                        hospitalList.add(Hospital_Model(
                                    "$hospitalImages",
                                    "$hospitalName",
                                    "$hospitalDescription",
                                    "$hospitalAddress",
                                    "$hospitalTel"
                                )
                            )
                        }
                    }

                    rvHospital_Lists.layoutManager = LinearLayoutManager(this)
                    rvHospital_Lists.setHasFixedSize(true)
                    rvHospital_Lists.adapter = HospitalAdapter(this, hospitalList){
                        //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, HospitalDetailActivity::class.java)
                        intent.putExtra(INTENT_PARCELABLE_hospital, it)
                        startActivity(intent)
                   // rvHospital_Lists.adapter?.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
    }

    /// Get data once from Firestore
    private fun getFirestoreHospitalResult() {
            FirebaseController.Firebase.db.collection("hospital")
                .get()
                .addOnSuccessListener { result ->
                 //   val hospitalList = ArrayList<Hospital_Model>()
                    for (document in result) {
                        Log.e(TAG, "${document.id} => ${document.data}")
                        val hospitalImages = document.getString("image")
                        val hospitalName = document.getString("name")
                        val hospitalDescription = document.getString("des")
                        val hospitalAddress = document.getString("add")
                        val hospitalTel = document.getString("tel")
                        hospitalList.add(
                            Hospital_Model(
                                    "$hospitalImages",
                                    "$hospitalName",
                                    "$hospitalDescription",
                                    "$hospitalAddress",
                                    "$hospitalTel"
                                )
                            )
                    }
                    rvHospital_Lists.layoutManager = LinearLayoutManager(this)
                    rvHospital_Lists.setHasFixedSize(true)
                    rvHospital_Lists.adapter = HospitalAdapter(this, hospitalList){
//                               Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, HospitalDetailActivity::class.java)
                        intent.putExtra(INTENT_PARCELABLE_hospital, it)
                        startActivity(intent)
                    rvHospital_Lists.adapter?.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
//        }
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
//        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
//        }
//    }

//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString("hospital_list", hospitalList.toString())
//
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        hospitalList = savedInstanceState.getParcelableArrayList(hospitalList.toString())!!
//
//    }



}


