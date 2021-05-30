package com.example.tvlonwer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tvlonwer.R
import com.example.tvlowner.viewModel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.security.acl.Owner

class Login : AppCompatActivity() {
    private var loginViewModel: LoginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(FirebaseAuth.getInstance().currentUser!=null){
            val value  = Intent(this, MainScreenActivity::class.java)
            value.putExtra("user", FirebaseAuth.getInstance().currentUser?.uid)
            value.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(value)
        }
    }

    fun SignIn(view: View){
        val email = findViewById<EditText>(R.id.email);
        val pass = findViewById<EditText>(R.id.pass);
        if(email.text.contains("@") && pass.text.length >=8){
            loginViewModel.login(email.text.toString(), pass.text.toString())
            Log.d ("Main ",this.loginViewModel.data.value?.uid.toString())
            loginViewModel.data.observe(this, Observer {
                val value  = Intent(this, MainScreenActivity::class.java)
                value.putExtra("user", FirebaseAuth.getInstance().currentUser?.uid)
                value.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(value)
            })
        }
        else{
            if(!email.text.contains("@"))
                email.error = "Incorrect email format"
            if(pass.text.length < 8)
                pass.error = "Password can not be less than 8 characters"
        }
    }



    fun onSignup(view: View) {
        val value  = Intent(this, SignUp::class.java)
        startActivity(value)
    }
}