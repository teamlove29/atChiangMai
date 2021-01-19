package com.alw.atchiangmai.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_police)

        val collectionPoliceFirestore = "policestation"
        getFirestorePoliceStationResult(collectionPoliceFirestore)
    }

    //Get data once from Firestore
    private fun getFirestorePoliceStationResult(collection: String){
        FirebaseController.Firebase.db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e(TAG, "${document.id} => ${document.data}")
                    val policeImages = document.getString("image")
                    val policeName = document.getString("name")
                    val policeDescription = document.getString("des")
                    val policeAddress = document.getString("add")
                    val policeTel = document.getString("tel")
                    when (collection) {
                        //   "otopFood" -> otopLists.add(OTOP_Model("$otopImages", "$otopText").toString())
                        "policestation" -> policeLists.add(
                            PoliceModel("$policeImages", "$policeName", "$policeDescription", "$policeAddress", "$policeTel")
                        )
                    }
                }
                rvPolice_Lists.layoutManager = LinearLayoutManager(this)
                rvPolice_Lists.setHasFixedSize(true)
                rvPolice_Lists.adapter = PoliceAdapter(this, policeLists){
                    //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, PoliceStationDetailActivity::class.java)
                    intent.putExtra(PoliceActivity.INTENT_PARCELABLE, it)
                    startActivity(intent)
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }
}