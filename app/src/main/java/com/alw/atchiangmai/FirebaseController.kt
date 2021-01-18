package com.alw.atchiangmai

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseController {
    object Firebase {
        val db = FirebaseFirestore.getInstance()
    }
}