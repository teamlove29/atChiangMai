package com.alw.atchiangmai.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
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
import kotlinx.android.synthetic.main.activity_otop.*

class OTOPActivity : AppCompatActivity(), CategoriesOTOPAdapter.OnItemCategoryClickListener {

    /// Firebase Firestore
    val TAG = "MyMessage"

    private var otopLists = ArrayList<OTOP_Model>()
    private var linearLayoutManager = LinearLayoutManager(this)

    //For Swipe Refresh
    var visibleThresholdOTOP = 1
    var firstItemVisible = 0
    var lastVisibleItem = 0
    var totalItemCount = 0
    var loading: Boolean = false

    //Array OTOP Category IMG
    private val categoryOTOPimg = arrayOf(
        R.drawable.ic_otop_food,
        R.drawable.ic_otop_drink,
        R.drawable.ic_otop_tshirt,
        R.drawable.ic_otop_accesory
    )

    //Array OTOP Category IMG
    private val categoryOTOPname = arrayOf("Food", "Drink", "Clothes", "Accessories")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otop)

        // Back Btn
        backBtnOTOP.setOnClickListener{
            finish()
        }


        getOtopItem()
        shimmerLayoutOTOP_Main_Horizontal.startShimmerAnimation()
        shimmerLayoutOTOP_Main_Vertical.startShimmerAnimation()

        addScrollerOTOPListener()

        //Swipe Refresh
        swipe_refresh_otop_layout.setOnRefreshListener {
            Handler().postDelayed({
                otopLists.clear()
                getOtopItem()
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
                        otopLists.removeAt(otopLists.size - 1)
                        loading = false
                        loadMoreOTOPData()
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

    /// Get data once from Firestore
    private fun firebaseFirestoreOTOP(collection : String) {
        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.e(TAG, "${document.id} => ${document.data}")
                    val otopImages = document.getString("image")
                    val otopText = document.getString("name")
//                    when (collection) {
                     //   "otopFood" -> otopLists.add(OTOP_Model("$otopImages", "$otopText").toString())
                    otopLists.add(OTOP_Model("$otopImages", "$otopText"))
//                    }
                }
                rvOTOP_Lists.adapter = OTOP_Adapter(this, otopLists)
                rvOTOP_Lists.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    // Test New Otop Data
    private fun getOtopItem(){
        val ref = db.collection("otop")
        ref.get().addOnCompleteListener {
            for ( dt in it.result!!){
                val ls = dt["list"] as ArrayList<*>
                for (doc in ls){
                    val data: MutableMap<*, *>? = doc as MutableMap<*, *>?
                    val images = data?.get("image").toString()
                    val name = data?.get("name").toString()
                    otopLists.add(OTOP_Model(images, name))
                }
            }
            rvOTOP_Lists.adapter = OTOP_Adapter(this, otopLists)
            rvOTOP_Lists.layoutManager = LinearLayoutManager(this)

            shimmerLayoutOTOP_Main_Horizontal.stopShimmerAnimation()
            shimmerLayoutOTOP_Main_Vertical.stopShimmerAnimation()
            shimmerLayoutOTOP_Main_Horizontal.visibility = View.GONE
            shimmerLayoutOTOP_Main_Vertical.visibility = View.GONE
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
           "Food" -> firebaseFirestoreOTOP("otopFood")
           "Drink" -> firebaseFirestoreOTOP("drink")
           "Clothes" -> firebaseFirestoreOTOP("shirt")
           "Accessories" -> firebaseFirestoreOTOP("acessories")
        }
    }

}