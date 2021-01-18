package com.alw.atchiangmai.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.FeedRecyclerAdapter
import com.alw.atchiangmai.Adapter.onCLickAdapterListener
import com.alw.atchiangmai.FirebaseController.Firebase.db
import com.alw.atchiangmai.Model.ItemDataFeed
import com.alw.atchiangmai.R
import com.ethanhua.skeleton.Skeleton
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.IOException


class FeedActivity : AppCompatActivity(), onCLickAdapterListener {

    private val itemDataFeed = ArrayList<ItemDataFeed>()
    private val itemDataActivity = ArrayList<ItemDataFeed>()
    private val itemDataRecommend = ArrayList<ItemDataFeed>()

    override fun onCreate(savedInstanceState: Bundle?) {
//        supportActionBar!!.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayHomeAsUpEnabled(true)
//        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val collectionFirebase = arrayListOf<String>("feed", "activity", "recommend")
        textViewTitleFeed.text = ""
        textViewTitleActivity.text = ""
        textViewTitleRecommend.text = ""
        shimmerLayoutFeed.startShimmerAnimation()
        shimmerLayoutActivity.startShimmerAnimation()
        shimmerLayoutRecommend.startShimmerAnimation()

                CoroutineScope(IO).launch {
                    for (value in collectionFirebase){
                        getResult(value)
                    }
                }
//            val loadDataOnFirebase = LoadDataOnFirebase(value)
//            loadDataOnFirebase.execute()
    }

    private suspend fun getResult(collection : String) : String {
            return withContext(Dispatchers.IO) {
                try {
                    db.collection(collection)
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    val image = document.getString("image")
                                    val title = document.getString("name")
                                    val description = document.getString("des")
                                    when(collection){
                                        "feed" -> itemDataFeed.add(ItemDataFeed("$image", "$title", "$description"))
                                        "activity" -> itemDataActivity.add(ItemDataFeed("$image", "$title", "$description"))
                                        "recommend" -> itemDataRecommend.add(ItemDataFeed("$image", "$title", "$description"))
                                    }
                                }

                                // AdapterFeed
                                feedRecyclerView.adapter = FeedRecyclerAdapter(itemDataFeed, this@FeedActivity)
                                feedRecyclerView.layoutManager = LinearLayoutManager(this@FeedActivity,LinearLayoutManager.HORIZONTAL,false)
                                textViewTitleFeed.text = "Feed"
                                shimmerLayoutFeed.stopShimmerAnimation()
                                shimmerLayoutFeed.setVisibility(View.GONE)
                                // AdapterActivity
                                activityRecyclerView.adapter = FeedRecyclerAdapter(itemDataActivity, this@FeedActivity)
                                activityRecyclerView.layoutManager = LinearLayoutManager(this@FeedActivity,LinearLayoutManager.HORIZONTAL,false)
                                textViewTitleActivity.text = "Activity"
                                shimmerLayoutActivity.stopShimmerAnimation()
                                shimmerLayoutActivity.setVisibility(View.GONE)
//                                // AdapterRecommend
                                recommendRecyclerView.adapter = FeedRecyclerAdapter(itemDataRecommend, this@FeedActivity)
                                recommendRecyclerView.layoutManager = LinearLayoutManager(this@FeedActivity,LinearLayoutManager.HORIZONTAL,false)
                                textViewTitleRecommend.text = "Recommend"
                                shimmerLayoutRecommend.stopShimmerAnimation()
                                shimmerLayoutRecommend.setVisibility(View.GONE)
                            }
                            .addOnFailureListener { exception ->
                                Log.d("error", "Error getting documents: ", exception)
                            }
                    collection
                }catch (ioe: IOException){
                    collection
                }
            }
    }


    override fun onClick(postion: Int) {
//        val intent = Intent(this,FeedActivity::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable("note",noteList[postion])
//        intent.putExtras(bundle)
//        startActivity(intent)
//        Toast.makeText(this,"Show : ${noteList[postion]}",Toast.LENGTH_LONG).show()
    }

}