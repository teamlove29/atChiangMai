package com.alw.atchiangmai.ui

import android.content.Intent
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

    private var hospitalListSearch: List<Hospital_Model> = ArrayList()

    private val collectionHospitalFirestore = "hospital"


    companion object {
        val INTENT_PARCELABLE_hospital = "OBJECT_INTENT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        // Firestore Collection

        getFirestoreHospitalResult(collectionHospitalFirestore )

//        editTextSearchHospital!!.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                hospitalList.clear()
//                searchInFirestore(s.toString().toLowerCase(Locale.ROOT))
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//
//        })

        searchViewHospital.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    searchHospitalInFirestore(query)
                }
//                else {
//                    getFirestoreHospitalResult(collectionHospitalFirestore )
//                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    searchHospitalInFirestore(newText)
                }
//                else {
//                    getFirestoreHospitalResult(collectionHospitalFirestore )
//                }
                return false
            }
        })

    }

    private fun searchHospitalInFirestore(searchText: String){
        // Search query
        println("Search text: $searchText")
        FirebaseController.Firebase.db.collection("hospital")
            .whereEqualTo("name", searchText)
            .get()
            .addOnSuccessListener { result ->
                val newArrayListHos = ArrayList<Hospital_Model>()
                if (result.isEmpty){
                    for (documentSearch in result){
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
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
    }

    /// Get data once from Firestore
    private fun getFirestoreHospitalResult(collection: String) {

        //If search text is empty will display all docs from hospital collection

            FirebaseController.Firebase.db.collection(collection)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.e(TAG, "${document.id} => ${document.data}")
                        val hospitalImages = document.getString("image")
                        val hospitalName = document.getString("name")
                        val hospitalDescription = document.getString("des")
                        val hospitalAddress = document.getString("add")
                        val hospitalTel = document.getString("tel")
                        when (collection) {
                            //   "otopFood" -> otopLists.add(OTOP_Model("$otopImages", "$otopText").toString())
                            "hospital" -> hospitalList.add(
                                Hospital_Model(
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
                    }

                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
//        }
    }



}

