package com.example.tvlonwer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tvlonwer.R

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.tvlonwer.viewModel.ForgetPasswordViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.Observer
class ForgetPassword : AppCompatActivity() {
    private lateinit var viewModel: ForgetPasswordViewModel
    private lateinit var email: TextView
    private lateinit var btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        FirebaseApp.initializeApp(this)

        email = findViewById(R.id.email)
        btn=findViewById(R.id.Reset)
        viewModel=ForgetPasswordViewModel()

    }

    fun forgetPassword(view: View){
        val email = findViewById<EditText>(R.id.email);
        viewModel.forgetPassword(email.text.toString())
        viewModel.data.observe(this, Observer {
            val value  = Intent(this, Login::class.java)
            value.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(value)
        })
    }
}