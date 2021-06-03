package com.example.tvlonwer.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Owner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var progressBar = findViewById<ProgressBar>(R.id.fetchUser)
        //progressBar.visibility = View.VISIBLE


        if (savedInstanceState == null) {
            update()
        }
        update()



    }
    fun update(){
        findViewById<EditText>(R.id.name).setText(Owner.displayName)
        findViewById<EditText>(R.id.email).setText(Owner.email())
        findViewById<EditText>(R.id.phoneNumber).setText(Owner.phone)
        findViewById<EditText>(R.id.cnic).setText(Owner.cnicNumber)


    }
    fun Back(view: View) {
        finish()
    }

    fun edit(view: View) {
        findViewById<TextView>(R.id.edit).visibility = View.INVISIBLE
        findViewById<Button>(R.id.logout).isClickable = false
        findViewById<Button>(R.id.save).isClickable = true
        findViewById<Button>(R.id.save).visibility = View.VISIBLE
        findViewById<Button>(R.id.logout).visibility = View.INVISIBLE
        findViewById<EditText>(R.id.name).isEnabled = true
        findViewById<EditText>(R.id.phoneNumber).isEnabled = true
        findViewById<EditText>(R.id.cnic).isEnabled = true
    }
    fun save(view: View){
        findViewById<TextView>(R.id.edit).visibility = View.VISIBLE
        findViewById<Button>(R.id.logout).isClickable = true
        findViewById<Button>(R.id.save).visibility = View.INVISIBLE
        findViewById<Button>(R.id.save).isClickable = false
        findViewById<Button>(R.id.logout).visibility = View.VISIBLE
        findViewById<EditText>(R.id.name).isEnabled = false
        findViewById<EditText>(R.id.phoneNumber).isEnabled = false
        findViewById<EditText>(R.id.cnic).isEnabled = false
        Owner.displayName = findViewById<EditText>(R.id.name).text.toString()
        Owner.cnicNumber = findViewById<EditText>(R.id.cnic).text.toString()
        Owner.phone = findViewById<EditText>(R.id.phoneNumber).text.toString()
        val db = FirebaseFirestore.getInstance()
        var col = db.collection("Admin").document(Owner.uid)
        col.update("name",Owner.displayName)
        col.update("cnic",Owner.cnicNumber)
        col.update("phone",Owner.phone)



    }

    fun Logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        val value  = Intent(this, Login::class.java)
        value.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(value)

    }
}