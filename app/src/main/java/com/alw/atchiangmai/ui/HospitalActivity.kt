package com.alw.atchiangmai.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.HospitalAdapter
import com.alw.atchiangmai.Adapter.OTOP_Adapter
import com.alw.atchiangmai.FirebaseController
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.Model.OTOP_Model
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_hospital.*
import kotlinx.android.synthetic.main.activity_otop.*

class HospitalActivity : AppCompatActivity() {

    private var TAG = "My Hospital Tag"
    private var hospitalList = ArrayList<Hospital_Model>()

    companion object {
        val INTENT_PARCELABLE_Hospital = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        val collectionHospitalFirestore = "hospital"
        getFirestoreHospitalResult(collectionHospitalFirestore)
    }

    /// Get data once from Firestore
    private fun getFirestoreHospitalResult(collection: String) {
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
                rvHospital_Lists.adapter = HospitalAdapter(this, hospitalList) {
                    //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HospitalDetailActivity::class.java)
                    intent.putExtra(INTENT_PARCELABLE_Hospital, it)
                    startActivity(intent)
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


}