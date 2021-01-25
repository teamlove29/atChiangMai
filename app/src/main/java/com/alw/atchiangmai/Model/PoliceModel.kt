package com.alw.atchiangmai.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PoliceModel(
    val cmpdImg: String,
    val cmpdName: String,
//    val cmpdDes: String,
    val cmpdAddress: String,
    val cmpdTel: String) : Parcelable {
}