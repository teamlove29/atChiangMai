package com.alw.atchiangmai.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Adapter.CategoriesOTOPAdapter
import com.alw.atchiangmai.Adapter.OTOP_Adapter
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.OTOP_Category_Model
import com.alw.atchiangmai.Model.OTOP_Model
import com.alw.atchiangmai.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_otop.*
import kotlinx.coroutines.*

//import com.prof.rssparser.Article

class OTOPActivity : AppCompatActivity(), CategoriesOTOPAdapter.OnItemCategoryClickListener {

    /// Firebase Firestore
    val TAG = "MyMessage"

    private var otopLists = ArrayList<OTOP_Model>()
    private var otopStoreLists = ArrayList<OTOP_Model>()
    private var linearLayoutManager = LinearLayoutManager(this)

    //For Swipe Refresh
    var visibleThresholdOTOP = 1
    var firstItemVisible = 0
    var lastVisibleItem = 0
    var totalItemCount = 0
    var loading: Boolean = false

    companion object {
        const val INTENT_PARCELABLE_otop = "OBJECT_INTENT"
    }

    //Array OTOP Category IMG
    private val categoryOTOPimg = arrayOf(
        R.drawable.ic_otop_food,
        R.drawable.ic_otop_drink,
        R.drawable.ic_otop_tshirt,
        R.drawable.ic_otop_accesory
    )


    //Array OTOP Category IMG
    private val categoryOTOPname = arrayOf("Food", "Drink", "Clothes", "Accessories")

    override fun onStart() {
        super.onStart()
//        if (otopLists.isEmpty()){
//            getOtopItem()
//        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otop)

        // Back Btn
        backBtnOTOP.setOnClickListener{
            finish()
        }

        icCartOTOP.setOnClickListener{
          //  Toast.makeText(this, "Go to Cart", Toast.LENGTH_SHORT).show()
        }


        getOtopItem()
//        shimmerLayoutOTOP_Main_Horizontal.startShimmerAnimation()
//        shimmerLayoutOTOP_Main_Vertical.startShimmerAnimation()

        addScrollerOTOPListener()

        //Swipe Refresh
        swipe_refresh_otop_layout.setOnRefreshListener {
            Handler().postDelayed({
                otopStoreLists.clear()
                otopLists.clear()
                rvOTOP_Lists.adapter!!.notifyDataSetChanged()
                swipe_refresh_otop_layout.isRefreshing = false
            }, 1000)
        }

        /////////////////// OTOP Categories /////////////////////
        val categoryList = ArrayList<OTOP_Category_Model>()
        for (categoryItemList in categoryOTOPname.indices) {

            categoryList.add(OTOP_Category_Model(  categoryOTOPimg[categoryItemList], categoryOTOPname[categoryItemList] ))

        }

        rvOTOP_categories.adapter = CategoriesOTOPAdapter(categoryList, this)
        rvOTOP_categories.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }



    private fun addScrollerOTOPListener(){
        rvOTOP_Lists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = linearLayoutManager.itemCount
                firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThresholdOTOP) && otopLists.size >= 10 &&
                        !swipe_refresh_otop_layout.isRefreshing && (otopLists.size % 10) == 0){

                    loading = true
                    otopLists.add(OTOP_Model("", "loadmore_otop"))

                    rvOTOP_Lists.adapter!!.notifyItemInserted(otopLists.size)
                    Handler().postDelayed({
                        if (otopLists.size > 10) {
                            otopLists.removeAt(otopLists.size - 1)
                            loading = false
                            loadMoreOTOPData()
                        }
                    }, 2000)
                }
            }
        })
    }

    private fun loadMoreOTOPData(){
        db.collection("otop")
            .orderBy("name")
            .startAfter(otopLists[otopLists.size - 1].otopItemText)
            .limit(10).get()
            .addOnCompleteListener{ value ->
                if (value.isSuccessful){
                    for (documentLoad in value.result!!){
                        val otopImages = documentLoad.getString("image").toString()
                        val otopText = documentLoad.getString("name").toString()

                        otopLists.add(OTOP_Model(otopImages, otopText))
                    }

                    rvOTOP_Lists.adapter!!.notifyItemChanged(
                        otopLists.size, otopLists.size
                    )
                }
            }
    }
    // Test New Otop Data
    private fun getOtopItem(){
        val ref = db.collection("otop")
        ref.get().addOnCompleteListener {
            for ( dt in it.result!!){
                val ls = dt["list"] as? ArrayList<*>
                if (ls != null) {
                    for (doc in ls){
                        val data: MutableMap<*, *>? = doc as MutableMap<*, *>?
                        val images = data?.get("image").toString()
                        val name = data?.get("name").toString()
                        otopStoreLists.add(OTOP_Model(images, name))
                    }
                }
            }

            // If both of data are equal, let's add them into adapter

                otopLists.addAll(otopStoreLists)
                rvOTOP_Lists.apply {
                    layoutManager = linearLayoutManager
                    adapter = OTOP_Adapter(this@OTOPActivity, otopLists)
                }


//            shimmerLayoutOTOP_Main_Horizontal.apply {
//                stopShimmerAnimation()
//                visibility = View.GONE
//            }
//
//            shimmerLayoutOTOP_Main_Vertical.apply {
//                stopShimmerAnimation()
//                visibility = View.GONE
//            }
        }
    }
    /// Get data once from Firestore
    private fun firebaseFirestoreOTOP(collection : String) {
        otopLists.clear()
        rvOTOP_Lists.adapter = null
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val otopImages = document.getString("image")
                    val otopText = document.getString("name")
//                    when (collection) {
//                        "otopFood" -> otopLists.add(OTOP_Model("$otopImages", "$otopText"))
//                    }
                    otopStoreLists.add(OTOP_Model("$otopImages", "$otopText"))
//                    }
                }

                // If both of data are equal, let's add them into adapter
                if (otopStoreLists.size == result.size()) {
                    otopLists.addAll(otopStoreLists)
                    rvOTOP_Lists.adapter = OTOP_Adapter(this, otopLists)
                    rvOTOP_Lists.layoutManager = linearLayoutManager
                }

//                shimmerLayoutOTOP_Main_Horizontal.apply {
//                    stopShimmerAnimation()
//                    visibility = View.GONE
//                }
//
//                shimmerLayoutOTOP_Main_Vertical.apply {
//                    stopShimmerAnimation()
//                    visibility = View.GONE
//                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

//    OTOP Categories Function
    override fun onItemClick(item: OTOP_Category_Model, position: Int){
        rvOTOP_Lists.adapter = null
        otopLists.clear()
        otopStoreLists.clear()
        when(item.cateOTText){
           "Food" -> firebaseFirestoreOTOP("otopFood")
           "Drink" -> firebaseFirestoreOTOP("drink")
           "Clothes" -> firebaseFirestoreOTOP("shirt")
           "Accessories" -> firebaseFirestoreOTOP("acessories")
        }
    }
}

