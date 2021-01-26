package com.alw.atchiangmai.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alw.atchiangmai.Adapter.ExchangeRecyclerAdapter
import com.alw.atchiangmai.Model.CurrencyData
import com.alw.atchiangmai.R
import com.alw.atchiangmai.api.ApiService
import com.alw.atchiangmai.api.Latest
import kotlinx.android.synthetic.main.activity_exchange.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
//        supportActionBar!!.apply {
//            title = "Exchange"
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(true)
//        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)

        val currencyList = ArrayList<CurrencyData>()
        currencyList.add(CurrencyData("JPY","ญี่ปุ่น : เยน"))
        currencyList.add(CurrencyData("GBP","อังกฤษ : ปอนด์"))
        currencyList.add(CurrencyData("USD","สหรัฐอเมริกา : ดอลลาร์"))
        currencyList.add(CurrencyData("EUR","ยูโรโซน : ยูโร"))
        currencyList.add(CurrencyData("HKD","ฮองกง : ดอลลาร์"))
        currencyList.add(CurrencyData("MYR","มาเลเซีย : กิงกิต"))
        currencyList.add(CurrencyData("SGD","สิงคโปร์ : ดอลลาร์"))
        currencyList.add(CurrencyData("CNY","จีน : ยวน"))

        exchangeRecyclerView.adapter = ExchangeRecyclerAdapter(currencyList)
        exchangeRecyclerView.layoutManager = LinearLayoutManager(this)


        apiService = ApiService()
        getLatest()
//        getAlbum(3)
//        textView.text = "Wait...."
//        getAlbums()

    }


// get api latest
    private fun getLatest(){
        val hashMap = hashMapOf<String,String>()
        hashMap["base"] = "JPY"
        hashMap["symbols"] = "THB,JPY"

        val call = apiService.getLatestIo(hashMap)
        call.enqueue(object : Callback<Latest> {
            override fun onResponse(call: Call<Latest>, response: Response<Latest>) {
                val list = response.body()
                val rate = list!!.rates["THB"]

                println("dasasdasdad ${rate}")
            }

            override fun onFailure(call: Call<Latest>, t: Throwable) {
                Log.e("API", "$t.message")
            }
        })
    }

//    private fun getAlbum(id:Int){
//        val call = apiService.getAlbum(id)
//        call.enqueue(object:Callback<Album>{
//            override fun onFailure(call: Call<Album>, t: Throwable) {
//                Log.e("API","$t.message")
//            }
//            override fun onResponse(call: Call<Album>, response: Response<Album>) {
//                val list = response.body()
//                    val msg = "id: ${list!!.id} \n userId: ${list!!.userId} \n title: ${list!!.album_name}"
////                    textView.text = msg
//            }
//
//        })
//    }

//    private fun getAlbums(){
//        val call = apiService.getAlbums()
//
//        call.enqueue(object:Callback<List<Album>>{
//            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
//                val list = response.body()
//                for (i in 0 until list!!.size){
//                    val msg = "id: ${list[i].id} \n userId: ${list[i].userId} \n title: ${list[i].album_name}"
////                    textView.append(msg)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
//                Log.e("API","$t.message")
//            }
//
//        })
//    }

}


