package com.example.tvlonwer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tvlonwer.view.Login
import com.example.tvlonwer.view.SignUp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val value  = Intent(this, Login::class.java)
        startActivity(value)
    }
}