package com.alw.atchiangmai.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alw.atchiangmai.Adapter.FavHotelAdapter
import com.alw.atchiangmai.FirebaseController
import com.alw.atchiangmai.Model.ModelCardPicText1
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.activity_hotel.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HotelActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        getFavHotel()
        toolBarHotel.setOnClickListener {
            finish()
        }

        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                editTextDate.setText(sdf.format(cal.time))

            }
        editTextDate.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun getFavHotel() {
        val ref = FirebaseController.Firebase.db.collection("activity")
        ref.get().addOnCompleteListener {
            val arrayList = ArrayList<ModelCardPicText1>()
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    val txt: String = doc["name"].toString()
                    val uri = Uri.parse(doc.get("image").toString())
                    arrayList.add(ModelCardPicText1(uri, txt, txt))
                }
            }
            val adapter = FavHotelAdapter(arrayList)
            rcv_FavHotel.layoutManager = GridLayoutManager(
                this,
                1,
                GridLayoutManager.HORIZONTAL,
                false
            )
            rcv_FavHotel.adapter = adapter
            adapter.notifyDataSetChanged()


        }


    }
}