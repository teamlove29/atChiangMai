package com.alw.atchiangmai.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
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
import kotlin.concurrent.schedule

class RestaurantActivity : AppCompatActivity(), onCLickAdapterListener {
    val foodList = ArrayList<RestaurantData>()
    val arrayList = ArrayList<RestaurantData>()
    var visibleThreshold = 1
    var lastVisibleItem = 0
    var totalItemCount = 0
    var loading: Boolean = false
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var adapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        linearLayoutManager = LinearLayoutManager(this@RestaurantActivity)
        adapter = RestaurantAdapter(foodList, this@RestaurantActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        shimmerLayoutRestaurant.startShimmerAnimation()
        getDataOnFirebase()
        restaurantSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                shimmerLayoutRestaurant.startShimmerAnimation()
                search(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                shimmerLayoutRestaurant.startShimmerAnimation()
                search(newText.toString())
                return true
            }
        })}

    private fun addScrollerListener(){
        restaurantRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // จำนวนข้อมูลที่แสดงทั้งหมด ณ ปัจจุบัน
                totalItemCount = linearLayoutManager.itemCount
                // จำนวนข้อมูลตัวสุดท้าย
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    loading = true

//                    foodList.add(RestaurantData("dasda", "loadmore", " ", " ", " ", " "))
//                    adapter.submitList(foodList)
                    Handler().postDelayed({
//                        getDataOnFirebase()
//                        RestaurantAdapter(foodList, this@RestaurantActivity).removeItem( RestaurantAdapter(foodList, this@RestaurantActivity).itemCount -1 )
//                        RestaurantAdapter(foodList, this@RestaurantActivity).addItem(foodList)

                        loading = false
                    }, 1500)

                }


            }
        })

    }


    fun search(value:String){
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
            shimmerLayoutRestaurant.stopShimmerAnimation()
            shimmerLayoutRestaurant.visibility = View.GONE
        }else{
            foodList.clear()
            foodList.addAll(arrayList)
            restaurantRecyclerView.adapter!!.notifyDataSetChanged()
            shimmerLayoutRestaurant.stopShimmerAnimation()
            shimmerLayoutRestaurant.visibility = View.GONE

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun getDataOnFirebase(){
        db.collection("restaurant")
            .get().addOnCompleteListener {
                if (it.isSuccessful){
                    println("dasadadasdsad ${it.result!!.size()}")
                    for(document in it.result!!){
                        val image = document.getString("image")
                        val title = document.getString("name")
                        val description = document.getString("des")
                        val rating = document.get("rating").toString()
                        val telephone = document.getString("tel")
                        val map = document.getString("map")
                        arrayList.add(RestaurantData("$image", "$title", rating, "$description", "$telephone", "$map"
                            )
                        )
                    }
                    foodList.addAll(arrayList)
                    restaurantRecyclerView.adapter = RestaurantAdapter(foodList, this@RestaurantActivity)
                    restaurantRecyclerView.layoutManager = linearLayoutManager
                    shimmerLayoutRestaurant.stopShimmerAnimation()
                    shimmerLayoutRestaurant.visibility = View.GONE


//                    addScrollerListener()

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
