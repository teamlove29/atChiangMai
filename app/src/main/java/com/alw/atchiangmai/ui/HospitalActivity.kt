package com.alw.atchiangmai.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Adapter.HospitalAdapter
import com.alw.atchiangmai.FirebaseController
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_hospital.*
import java.util.*
import kotlin.collections.ArrayList

class HospitalActivity: AppCompatActivity() {

    private var TAG = "My Hospital Tag"

    private var hospitalList = ArrayList<Hospital_Model>()
    private var hospitalStoreList = ArrayList<Hospital_Model>()
    private var linearLayoutManager = LinearLayoutManager(this)
    var visibleThresholdHospital = 1
    var firstItemVisible = 0
    var lastVisibleItem = 0
    var totalItemCount = 0
    var loading: Boolean = false

    companion object {
        const val INTENT_PARCELABLE_hospital = "OBJECT_INTENT"
    }

    override fun onStart() {
        super.onStart()
        if (hospitalList.isEmpty()){
            getFirestoreHospitalResult()
        }
    }

    override fun onResume() {
        super.onResume()
        searchViewHospital.clearFocus()
        searchViewHospital.setQuery("",false)
//        searchViewHospital.isIconified = false;
        rvHospital_Lists.adapter?.notifyDataSetChanged()
//        hospitalList.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        shimmerLayoutHos1.startShimmerAnimation()

        addScrollerHospitalListener()
        //Swipe Refresh
        swipe_refresh_hospital_layout.setOnRefreshListener {
            Handler().postDelayed({
                hospitalStoreList.clear()
                hospitalList.clear()
                getFirestoreHospitalResult()
                rvHospital_Lists.adapter!!.notifyDataSetChanged()
                swipe_refresh_hospital_layout.isRefreshing = false
            },1000)
        }


        searchViewHospital.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(queryHospital: String?): Boolean {
//                if (queryHospital != null){
//                    hospitalList.clear()
//                    searchHospitalInFirestore(queryHospital)
//                }
//                else {
//                    getFirestoreHospitalResult()
//                }
                searchHospitalInFirestore(queryHospital.toString())
                shimmerLayoutHos1.startShimmerAnimation()
                return false
            }
            override fun onQueryTextChange(newTextHospital: String?): Boolean {
//                if (newTextHospital != null){
//                    hospitalList.clear()
//                    searchHospitalInFirestore(newTextHospital)
//                }
//                else {
//                    getFirestoreHospitalResult()
//                }
                shimmerLayoutHos1.startShimmerAnimation()
                searchHospitalInFirestore(newTextHospital.toString())
               return false
            }
        })
    }

    override fun onStop() {
        super.onStop()
        searchViewHospital.setQuery(null, true)
    }

    private fun addScrollerHospitalListener(){
        rvHospital_Lists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = linearLayoutManager.itemCount
                // Amount of 1st data
                firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition()
                // Amount of last data
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThresholdHospital) && hospitalList.size >= 10 &&
                        !swipe_refresh_hospital_layout.isRefreshing && (hospitalList.size % 10) == 0){
                    loading = true

                    hospitalList.add(
                        Hospital_Model("",
                        "loadmore_hospital",
                        "",
                        "",
                        "")
                    )
                    rvHospital_Lists.adapter!!.notifyItemInserted(hospitalList.size)

                    Handler().postDelayed({
                        hospitalList.removeAt(hospitalList.size - 1)
                        loading = false
                        loadMoreHospitalData()
                    }, 2000)
                }
            }
        })
    }

    private fun loadMoreHospitalData(){
        FirebaseController.Firebase.db.collection("hospital")
            .orderBy("name")
            .startAfter(hospitalList[hospitalList.size - 1].hospitalName)
            .limit(10).get()
            .addOnCompleteListener{ value ->
                if (value.isSuccessful){
                    for (documentLoad in value.result!!){
                        val hospitalImages = documentLoad.getString("image")
                        val hospitalName = documentLoad.getString("name")
                        val hospitalDescription = documentLoad.getString("des")
                        val hospitalAddress = documentLoad.getString("add")
                        val hospitalTel = documentLoad.getString("tel")

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
                    rvHospital_Lists.adapter!!.notifyItemChanged(
                        hospitalList.size,
                        hospitalList.size
                    )

                }
            }
    }

    private fun searchHospitalInFirestore(searchHosTxt: String){
        if(searchHosTxt.isNotEmpty()){
            hospitalList.clear()
            db.collection("hospital")
                .whereEqualTo("name", searchHosTxt)
                .get()
                .addOnCompleteListener { task ->
                    //  val hospitalList = ArrayList<Hospital_Model>()
                    if (task.isSuccessful){
                        for (documentSearch in task.result!!){
    //                        Log.e(TAG, "${documentSearch.id} => ${documentSearch.data}")
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

                    rvHospital_Lists.layoutManager = linearLayoutManager
                    rvHospital_Lists.setHasFixedSize(true)
                    shimmerLayoutHos1.apply {
                        stopShimmerAnimation()
                        visibility = View.GONE
                    }

                    rvHospital_Lists.adapter = HospitalAdapter(this, hospitalList){
                        //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, HospitalDetailActivity::class.java)
                        intent.putExtra(INTENT_PARCELABLE_hospital, it)
                        startActivity(intent)
    //                        overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                        // rvHospital_Lists.adapter?.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
        }else{
            hospitalList.clear()
            hospitalList.addAll(hospitalStoreList)
            rvHospital_Lists.adapter = HospitalAdapter(this, hospitalList){
                val intent = Intent(this, HospitalDetailActivity::class.java)
                intent.putExtra(INTENT_PARCELABLE_hospital, it)
                startActivity(intent)
            }
            shimmerLayoutHos1.apply {
                stopShimmerAnimation()
                visibility = View.GONE
            }
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
                    rvHospital_Lists.layoutManager = linearLayoutManager
                    rvHospital_Lists.setHasFixedSize(true)
                    rvHospital_Lists.adapter = HospitalAdapter(this, hospitalList){
//                               Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, HospitalDetailActivity::class.java)
                        intent.putExtra(INTENT_PARCELABLE_hospital, it)
                        startActivity(intent)
//                        overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
//                        rvHospital_Lists.adapter?.notifyDataSetChanged()
                    }
                    shimmerLayoutHos1.stopShimmerAnimation()
                    shimmerLayoutHos1.visibility = View.GONE
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
    }
}
