package com.example.tvlonwer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.tvlonwer.R
import com.example.tvlowner.viewModel.SignUpViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private var viewModel: SignUpViewModel = SignUpViewModel()
    private lateinit var email: TextView
    private lateinit var name: TextView
    private lateinit var password: TextView
    private lateinit var repassword: TextView
    private lateinit var cnic: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        FirebaseApp.initializeApp(this)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.pass)
        repassword = findViewById(R.id.cpass)
        cnic = findViewById(R.id.cnic)
        phoneNumber = findViewById(R.id.phone)
        progressBar = findViewById(R.id.progressBar)
        btn= findViewById(R.id.signUp)

        viewModel.result.observe(this, Observer {
            progressBar.visibility = View.GONE
            name.isEnabled = true
            email.isEnabled = true
            password.isEnabled = true
            repassword.isEnabled = true
            cnic.isEnabled = true
            phoneNumber.isEnabled = true
            progressBar.isEnabled = true
            btn.isEnabled = true
            val value  = Intent(this, Login::class.java)
            value.putExtra("user", FirebaseAuth.getInstance().currentUser?.uid)
            value.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(value)
        })
    }

    fun onSignUp(view: View) {

        if (!email.text.contains("@"))
            email.error = "Enter valid email"
        else if (password.text.length < 8 && !password.text.equals(repassword.text))
            password.error = "Invalid Passwords"
        else {
            progressBar.visibility = View.VISIBLE
            name.isEnabled = false
            email.isEnabled = false
            password.isEnabled = false
            repassword.isEnabled = false
            cnic.isEnabled = false
            phoneNumber.isEnabled = false
            progressBar.isEnabled = false
            btn.isEnabled = false
            viewModel.createAccount(
                email.text.toString(), password.text.toString(), name.text.toString(),
                cnic.text.toString(), phoneNumber.text.toString(), this
            )
        }
    }

    fun onSignIn(view: View) {
        val value  = Intent(this, Login::class.java)
        startActivity(value)
    }
}