package com.alw.atchiangmai.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RestaurantData(val image:String, val name:String, val rating:String,val description:String,var tel:String, var map:String):Parcelable