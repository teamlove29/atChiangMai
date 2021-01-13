package com.alw.atchiangmai.ui

import android.os.Bundle
import android.util.Log
import android.widget.Switch
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


class FeedActivity : AppCompatActivity(),onNoteCLickListener{

    private val TAG = "FirebaseEmailPassword"
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


        val itemsGroup = ArrayList<ItemGroupFeed>()
        val itemData = ArrayList<ItemDataFeed>()
        mAuth = FirebaseAuth.getInstance()

        val email = "ScreenAnyWhere@gmail.com"
        val password = "123456"
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.e(TAG, "signIn: Success!")
                        // update UI with the signed-in user's information
                        val user = mAuth!!.getCurrentUser()
//                        updateUI(user)
                    } else {
                        Log.e(TAG, "signIn: Fail!", task.exception)
                        Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                    }

                }

        val db = FirebaseFirestore.getInstance()
        val collectionFirebase = arrayListOf<String>("feed","activity","recommend")

        collectionFirebase.forEach{
                collection ->
            db.collection(collection)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val image = document.getString("image")
                        val title = document.getString("name")
                        val description = document.getString("des")
//                                Log.d(i, "${document.getString("name")}")
                        itemData.add(ItemDataFeed("$image", "$title", "$description"))
                    }
                    itemsGroup.add(ItemGroupFeed("$collection ", itemData))
                    feedRecyclerViewPage.adapter = GroupFeedAdapter(itemsGroup, this)
                    feedRecyclerViewPage.layoutManager = LinearLayoutManager(this)
                }
                .addOnFailureListener { exception ->
                    Log.d("error", "Error getting documents: ", exception)
                }
        }



//        }







    }


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