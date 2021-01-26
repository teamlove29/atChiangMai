package com.alw.atchiangmai.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Adapter.RestaurantAdapter
import com.alw.atchiangmai.Adapter.onCLickAdapterListener
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.RestaurantData
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_restaurant.*
import java.util.*
import kotlin.collections.ArrayList

class RestaurantActivity : AppCompatActivity(), onCLickAdapterListener {
    val foodList = ArrayList<RestaurantData>()
    val arrayList = ArrayList<RestaurantData>()
    var visibleThreshold = 1
    var firstItemVisible = 0
    var lastVisibleItem = 0
    var totalItemCount = 0
    var loading: Boolean = false
    lateinit var linearLayoutManager: LinearLayoutManager
//    lateinit var adapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        linearLayoutManager = LinearLayoutManager(this@RestaurantActivity)
//        adapter = RestaurantAdapter(foodList, this@RestaurantActivity)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        shimmerLayoutRestaurant.startShimmerAnimation()
        if (foodList.isEmpty()){
            getDataOnFirebase()
        }
        addScrollerListener()

        swipe_refresh_layout.setOnRefreshListener {
            Handler().postDelayed({
                arrayList.clear()
                foodList.clear()
                getDataOnFirebase()
                restaurantRecyclerView.adapter!!.notifyDataSetChanged()
                swipe_refresh_layout.isRefreshing = false
            }, 1500)
        }

            restaurantSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                        search(query.toString())
                    shimmerLayoutRestaurant.startShimmerAnimation()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    shimmerLayoutRestaurant.startShimmerAnimation()
                        search(newText.toString())
                    return false
                }
            })


    }

    override fun onStop() {
        super.onStop()
        restaurantSearchView.setQuery(null,true)
    }

    private fun addScrollerListener(){
            restaurantRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // จำนวนข้อมูลที่แสดงทั้งหมด ณ ปัจจุบัน
                    totalItemCount = linearLayoutManager.itemCount
                    // จำนวนข้อมูลตัวแรก
                    firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition()
                    // จำนวนข้อมูลตัวสุดท้าย
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && foodList.size >= 10 && !swipe_refresh_layout.isRefreshing && (foodList.size % 10) == 0) {
                        loading = true

                        foodList.add(RestaurantData("", "loadmore", " ", " ", " ", " "))
                        restaurantRecyclerView.adapter!!.notifyItemInserted(foodList.size)

                        Handler().postDelayed({
                            foodList.removeAt(foodList.size - 1)
                            loading = false
                            loadMoreData()
                        }, 2000)

                    }
                }
            })

    }

    fun loadMoreData(){

            db.collection("restaurant")
                .orderBy("name")
                .startAfter(foodList[foodList.size - 1].name)
                .limit(10).get()
                .addOnCompleteListener { value ->
            if (value.isSuccessful){
                for(document in value.result!!){
                    val image = document.getString("image")
                    val title = document.getString("name")
                    val description = document.getString("des")
                    val rating = document.get("rating").toString()
                    val telephone = document.getString("tel")
                    val map = document.getString("map")
                    foodList.add(
                        RestaurantData(
                            "$image",
                            "$title",
                            rating,
                            "$description",
                            "$telephone",
                            "$map"
                        )
                    )
                }
                restaurantRecyclerView.adapter!!.notifyItemRangeChanged(
                    foodList.size,
                    foodList.size
                )
            }
        }
    }

    fun search(value: String){
            if (value.isNotEmpty()){
                foodList.clear()
                // นำมาปรับตัวให้เป็นตัวเล็กทั้งหมด
                val search = value.toLowerCase(Locale.getDefault())
                arrayList.forEach {
                    // นำมาปรับตัวให้เป็นตัวเล็กทั้งหมด และนำมาเทียบกันว่ามีประโยคไหนเหมือนกันก็ให้โชว์
                    if (it.name.toLowerCase(Locale.getDefault()).contains(search)){
                        foodList.add(it)
                    }
                }
                restaurantRecyclerView.adapter!!.notifyDataSetChanged()
                shimmerLayoutRestaurant.stopShimmerAnimation().apply {
                    shimmerLayoutRestaurant.visibility = View.GONE
                }
            }else{
                foodList.clear()
                foodList.addAll(arrayList)
                restaurantRecyclerView.adapter = RestaurantAdapter(foodList,this)
                shimmerLayoutRestaurant.stopShimmerAnimation()
                shimmerLayoutRestaurant.visibility = View.GONE
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun getDataOnFirebase(){

        val ref =  db.collection("restaurant").limit(10).orderBy("name")

        ref.get().addOnCompleteListener { value ->

                if (value.isSuccessful){
                    for(document in value.result!!){
                        val image = document.getString("image")
                        val title = document.getString("name")
                        val description = document.getString("des")
                        val rating = document.get("rating").toString()
                        val telephone = document.getString("tel")
                        val map = document.getString("map")
                        arrayList.add(
                            RestaurantData(
                                "$image",
                                "$title",
                                rating,
                                "$description",
                                "$telephone",
                                "$map"
                            )
                        )
                    }
                    foodList.addAll(arrayList)
                    restaurantRecyclerView.adapter = RestaurantAdapter(
                        foodList,
                        this@RestaurantActivity
                    )
                    restaurantRecyclerView.layoutManager = linearLayoutManager

                    shimmerLayoutRestaurant.stopShimmerAnimation()
                    shimmerLayoutRestaurant.visibility = View.GONE
                }
            }

            }


    override fun onClick(postion: Int) {

        val intent = Intent(this, DetailRestaurantActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("restaurant", foodList[postion])
        intent.putExtras(bundle)
        startActivity(intent)
    }


}


