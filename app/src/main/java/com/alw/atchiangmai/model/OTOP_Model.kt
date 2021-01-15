package com.alw.atchiangmai.model

import android.graphics.Bitmap

data class OTOP_Model(
    val otopImage: String,
    val otopItemText: String) {
}

data class OTOP_Category_Model(val cateOTimg: Bitmap, val cateOTText: String)