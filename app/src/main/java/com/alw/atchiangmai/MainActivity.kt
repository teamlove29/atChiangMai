package com.alw.atchiangmai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.GroupFeedAdapter
import com.alw.atchiangmai.Adapter.onNoteCLickListener
import com.alw.atchiangmai.Model.ItemDataFeed
import com.alw.atchiangmai.Model.ItemGroupFeed
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),onNoteCLickListener{
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main)


        val itemsGroup = ArrayList<ItemGroupFeed>()
        val itemData = ArrayList<ItemDataFeed>()
        for (i in 0..2) {
            itemData.add(ItemDataFeed(R.drawable.placeholder1024x640, "Title$i", "Description$i"))
            itemsGroup.add(ItemGroupFeed("$i", itemData))
        }

        outerRecyclerView.adapter = GroupFeedAdapter(itemsGroup,this)
        outerRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(postion: Int) {
        val intent = Intent(this,FeedActivity::class.java)
        val bundle = Bundle()
//        bundle.putParcelable("note",noteList[postion])
//        intent.putExtras(bundle)
        startActivity(intent)
//        Toast.makeText(this,"Show : ${noteList[postion]}",Toast.LENGTH_LONG).show()
    }


}