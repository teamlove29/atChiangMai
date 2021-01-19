package com.alw.atchiangmai.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.RestaurantAdapter
import com.alw.atchiangmai.Adapter.onCLickAdapterListener
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.RestaurantData
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_restaurant.*
import java.util.*
import kotlin.collections.ArrayList

class RestaurantActivity : AppCompatActivity(), onCLickAdapterListener {
    val foodList = ArrayList<RestaurantData>()
    val arrayList = ArrayList<RestaurantData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        getDataOnFirebase()

        restaurantSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText.toString())
                return true
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
        }else{
            foodList.clear()
            foodList.addAll(arrayList)
            restaurantRecyclerView.adapter!!.notifyDataSetChanged()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun getDataOnFirebase(){
        db.collection("restaurant")
            .get().addOnCompleteListener {
                if (it.isSuccessful){
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
                    restaurantRecyclerView.adapter = RestaurantAdapter(foodList, this)
                    restaurantRecyclerView.layoutManager = LinearLayoutManager(this)

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
