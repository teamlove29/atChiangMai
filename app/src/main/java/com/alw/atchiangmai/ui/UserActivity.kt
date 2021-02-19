package com.alw.atchiangmai.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alw.atchiangmai.R
import com.firebase.ui.auth.AuthUI
import org.xms.f.auth.ExtensionAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        checkingUser()
    }

    private fun checkingUser(){
        if(ExtensionAuth.getInstance().currentUser?.uid == null) {
            val providers = arrayListOf(
//                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
//                   .setTheme(R.style.LoginTheme)
                    .build(),
                1
            )
        }else {
            txt_user_name.text = "${ExtensionAuth.getInstance().currentUser?.displayName}"


            if (ExtensionAuth.getInstance().currentUser?.photoUrl !== null) {
                Picasso.get().load(ExtensionAuth.getInstance().currentUser?.photoUrl)
                    .into(image_user)

            } else {
                image_user.setImageResource(R.drawable.baseline_account_circle_black_24dp)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            Picasso.get().load(ExtensionAuth.getInstance().currentUser?.photoUrl)
                .into(image_user)
            txt_user_name.text = "${ExtensionAuth.getInstance().currentUser?.displayName}"
        }
    }
}