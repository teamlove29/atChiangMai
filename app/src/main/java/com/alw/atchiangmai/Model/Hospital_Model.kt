package com.alw.atchiangmai.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Hospital_Model(val hospitalImg: String,
                          val hospitalName: String,
                          val hospitalDes: String,
                          val hospitalAddress: String,
                          val hospitalTel: String
                          ) : Parcelable