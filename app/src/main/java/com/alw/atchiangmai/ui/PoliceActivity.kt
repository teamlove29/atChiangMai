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
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Adapter.HospitalAdapter
import com.alw.atchiangmai.Adapter.PoliceAdapter
import com.alw.atchiangmai.FirebaseController
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.Hospital_Model
import com.alw.atchiangmai.Model.PoliceModel
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_hospital.*
import kotlinx.android.synthetic.main.activity_police.*

class PoliceActivity : AppCompatActivity() {

    private var TAG = "My Police Station Tag"

    private var policeLists = ArrayList<PoliceModel>()
    private var policeStoreLists = ArrayList<PoliceModel>()
    private var linearLayoutManager = LinearLayoutManager(this)
    var visibleThresholdPolice = 1
    var firstPolItemVisible = 0
    var lastPolItemVisible = 0
    var totalPolItemCount = 0
    var loadingPol: Boolean = false

    companion object{
        val INTENT_PARCELABLE_Police = "OBJECT_INTENT"
    }

    override fun onStart(){
        super.onStart()
        if (policeLists.isEmpty()){
            getFirestorePoliceStationResult()
        }
    }

    override fun onResume() {
        super.onResume()
        searchViewPoliceDepartment.clearFocus()
        searchViewPoliceDepartment.setQuery("", false)
        rvPolice_Lists.adapter?.notifyDataSetChanged()
//        policeLists.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_police)

        //Back Btn
        backBtnPoliceStation.setOnClickListener{
            finish()
        }

        shimmerLayoutPolice1.startShimmerAnimation()

        addScrollerPoliceListener()

        //Swipe Refresh
        swipe_refresh_police_layout.setOnRefreshListener {
            Handler().postDelayed({
                policeStoreLists.clear()
                policeLists.clear()
                getFirestorePoliceStationResult()
                rvPolice_Lists.adapter!!.notifyDataSetChanged()
                swipe_refresh_police_layout.isRefreshing = false
            }, 1000)
        }

        searchViewPoliceDepartment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryPolice: String?): Boolean {
//                if(queryPolice != null){
//                    policeLists.clear()
//                    searchPoliceStationInFirestore(queryPolice)
//                }
//                else{
//                    getFirestorePoliceStationResult()
//                }
//                return false
                searchPoliceStationInFirestore(queryPolice.toString())
                shimmerLayoutPolice1.startShimmerAnimation()
                return false
            }

            override fun onQueryTextChange(newTextPoliceStation: String?): Boolean {
//                if (newTextPoliceStation != null){
//                    policeLists.clear()
//                    searchPoliceStationInFirestore(newTextPoliceStation)
//                }
//                else {
//                    getFirestorePoliceStationResult()
//                }
//                return false
                shimmerLayoutPolice1.startShimmerAnimation()
                searchPoliceStationInFirestore(newTextPoliceStation.toString())
                return false
            }
        })
    }

    override fun onStop() {
        super.onStop()
        searchViewPoliceDepartment.setQuery(null, true)
    }

    private fun addScrollerPoliceListener(){
        rvPolice_Lists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalPolItemCount = linearLayoutManager.itemCount
                // Amount of 1st data
                firstPolItemVisible = linearLayoutManager.findFirstVisibleItemPosition()
                // Amount of last data
                lastPolItemVisible = linearLayoutManager.findLastVisibleItemPosition()

                if (!loadingPol && totalPolItemCount <= (lastPolItemVisible + visibleThresholdPolice) && policeLists.size >= 10 &&
                        !swipe_refresh_police_layout.isRefreshing && (policeLists.size % 10) == 0){
                    loadingPol = true

                    policeLists.add(PoliceModel("",
                        "loadmore_police_station",
                        "",
                        ""))
                }
                rvPolice_Lists.adapter!!.notifyItemInserted(policeLists.size)

//              Bug for 1 Index
//                Handler().postDelayed({
//                    if (policeLists.size == 1){
//                        policeLists.removeAt(policeLists.size)
//                        loadingPol = false
//                        loadMorePoliceStationData()
//                    }else{
//                        policeLists.removeAt(policeLists.size - 1)
//                        loadingPol = false
//                        loadMorePoliceStationData()
//                    }
//
//                }, 2000)
            }
        })
    }

    private fun loadMorePoliceStationData() {
        db.collection("policestation")
            .orderBy("name")
            .startAfter(policeLists[policeLists.size].cmpdName)
            .limit(10).get()
            .addOnCompleteListener{ value ->
                if (value.isSuccessful){
                    for (documentLoad in value.result!!){
                        val policeImages = documentLoad.getString("image").toString()
                        val policeName = documentLoad.getString("name").toString()
//                      val policeDescription = documentLoad.getString("des").toString()
                        val policeAddress = documentLoad.getString("add").toString()
                        val policeTel = documentLoad.getString("tel").toString()

                        policeLists.add(
                            PoliceModel(
                                policeImages,
                                policeName,
                                policeAddress,
                                policeTel
                            )
                        )
                    }
                    rvPolice_Lists.adapter!!.notifyItemChanged(
                        policeLists.size,
                        policeLists.size
                    )
                }
            }
    }

    //Search Function
    private fun searchPoliceStationInFirestore(searchPolTxt: String){
       if (searchPolTxt.isNotEmpty()){
           db.collection("policestation")
               .whereEqualTo("name", searchPolTxt)
               .get()
               .addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       for (documentSearch in task.result!!) {
//                           Log.e(TAG, "${documentSearch.id} => ${documentSearch.data}")
                           val policeImages = documentSearch.getString("image")
                           val policeName = documentSearch.getString("name")
                           val policeAddress = documentSearch.getString("add")
                           val policeTel = documentSearch.getString("tel")

                           policeLists.add(PoliceModel(
                                   "$policeImages",
                                   "$policeName",
                                   "$policeAddress",
                                   "$policeTel"
                               )
                           )
                       }
                   }
                   rvPolice_Lists.layoutManager = linearLayoutManager
                   rvPolice_Lists.setHasFixedSize(true)
                   shimmerLayoutPolice1.apply{
                       stopShimmerAnimation()
                       visibility = View.GONE
                   }

                   rvPolice_Lists.adapter = PoliceAdapter(this, policeLists) {
                       //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                       val intent = Intent(this, PoliceStationDetailActivity::class.java)
                       intent.putExtra(PoliceActivity.INTENT_PARCELABLE_Police, it)
                       startActivity(intent)
//                       overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                   }
               }
               .addOnFailureListener { exception ->
                   Log.d(TAG, "Error getting documents: ", exception)
               }
       }else{
           policeLists.clear()
           policeLists.addAll(policeStoreLists)
           rvPolice_Lists.adapter = PoliceAdapter(this, policeLists){
               val intent = Intent(this, HospitalDetailActivity::class.java)
               intent.putExtra(HospitalActivity.INTENT_PARCELABLE_hospital, it)
               startActivity(intent)
           }
           shimmerLayoutPolice1.apply {
               stopShimmerAnimation()
               visibility = View.GONE
           }
       }
    }

    //Get data once from Firestore
    private fun getFirestorePoliceStationResult(){
        FirebaseController.Firebase.db.collection("policestation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.e(TAG, "${document.id} => ${document.data}")
                    val policeImages = document.getString("image")
                    val policeName = document.getString("name")
//                    val policeDescription = document.getString("des")
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
                rvPolice_Lists.layoutManager = linearLayoutManager
                rvPolice_Lists.setHasFixedSize(true)
                rvPolice_Lists.adapter = PoliceAdapter(this, policeLists){
                    //       Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, PoliceStationDetailActivity::class.java)
                    intent.putExtra(PoliceActivity.INTENT_PARCELABLE_Police, it)
                    startActivity(intent)
//                    overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
                }
                shimmerLayoutPolice1.apply {
                    stopShimmerAnimation()
                    visibility = View.GONE
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}