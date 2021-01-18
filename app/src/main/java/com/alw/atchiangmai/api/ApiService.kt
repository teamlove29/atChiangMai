package com.alw.atchiangmai.api
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

val BASE_URL ="https://jsonplaceholder.typicode.com/"

interface ApiService{
    @GET("albums")
    fun getAlbums() : Call<List<Album>>

    @GET("albums/{id}")
    fun getAlbum(@Path("id") id :Int ) : Call<Album>

    @GET("albums/")
    fun getUserIdFromAlbum(@QueryMap map : HashMap<String,String> ) : Call<Album>

    companion object{
       operator fun invoke() : ApiService{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}