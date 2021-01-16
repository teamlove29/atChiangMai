package com.alw.atchiangmai.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.GroupFeedAdapter
import com.alw.atchiangmai.Adapter.onNoteCLickListener
import com.alw.atchiangmai.Model.ItemDataFeed
import com.alw.atchiangmai.Model.ItemGroupFeed
import com.alw.atchiangmai.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.IOException


class FeedActivity : AppCompatActivity(),onNoteCLickListener{
    private val TAG = "FirebaseEmailPassword"
    private var mAuth: FirebaseAuth? = null

    private val itemsGroup = ArrayList<ItemGroupFeed>()
    private val itemDataFeed = ArrayList<ItemDataFeed>()
    private val itemDataActivity = ArrayList<ItemDataFeed>()
    private val itemDataRecommend = ArrayList<ItemDataFeed>()

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        mAuth = FirebaseAuth.getInstance()
        val email = "ScreenAnyWhere@gmail.com"
        val password = "123456"
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.e(TAG, "signIn: Success!")
                        // update UI with the signed-in user's information
//                        val user = mAuth!!.getCurrentUser()
//                        updateUI(user)
                    } else {
                        Log.e(TAG, "signIn: Fail!", task.exception)
                        Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }
                }

        val collectionFirebase = arrayListOf<String>("feed", "activity", "recommend")

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
                                when(collection){
                                    "feed" -> itemsGroup.add(ItemGroupFeed("Feed", itemDataFeed))
                                    "activity" -> itemsGroup.add(ItemGroupFeed("Activity", itemDataActivity))
                                    "recommend" -> itemsGroup.add(ItemGroupFeed("Recommend", itemDataRecommend))
                                }
                                feedRecyclerViewPage.adapter = GroupFeedAdapter(itemsGroup, this@FeedActivity)
                                feedRecyclerViewPage.layoutManager = LinearLayoutManager(this@FeedActivity)
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


// inner class LoadDataOnFirebase(var collection : String) : AsyncTask<String,String,String>(){
//    override fun doInBackground(vararg params: String?): String {
//       return ""
//    }
//
//    override fun onPostExecute(result: String?) {
//        super.onPostExecute(result)
//
//    }
//}

    override fun onClick(postion: Int) {
//        val intent = Intent(this,FeedActivity::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable("note",noteList[postion])
//        intent.putExtras(bundle)
//        startActivity(intent)
//        Toast.makeText(this,"Show : ${noteList[postion]}",Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
//        updateUI(currentUser)
    }

}