package com.example.tvlonwer.view

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tvlonwer.R
import com.example.tvlonwer.viewModel.FeedbackViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle as Bundle1


class Feedback : AppCompatActivity() {
    private var viewModel = FeedbackViewModel()
    private var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        val id:String = intent.getStringExtra("Vendor_Id").toString()
        val name:String=intent.getStringExtra("Vendor_Name").toString()
        //val name:String=bundle.getExtras("Vendor_Id")

       // Toast.makeText(this,name+" "+id, Toast.LENGTH_LONG).show()
        val feedbackButton=findViewById<View>(R.id.feedback_button)
        feedbackButton.setOnClickListener {
            val descripition:String  =findViewById<EditText>(R.id.label_description).text.toString()
            val rBar = findViewById<RatingBar>(R.id.rating_bar)
            val msg = rBar.rating.toString()
            //Toast.makeText(this,msg+" "+descripition, Toast.LENGTH_LONG).show()
            //DB

            var ownerId = FirebaseAuth.getInstance().currentUser
            auth = FirebaseAuth.getInstance()
            viewModel.addFeedback(descripition,msg,id,name)
            val intent = Intent(this, MainScreenActivity::class.java)
            Toast.makeText(this,"Feedback Submitted", Toast.LENGTH_LONG).show()
            startActivity(intent)


        }
            /*viewModel.addApointment(approved ,ownerId.uid , time , uservehicle_Id, vendor_Id)
            viewModel.result.observe(requireActivity(),{
                if(viewModel.result.value == true){
                    val fragmentManager: FragmentManager? = fragmentManager
                    val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, ViewAppointments())
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
                else{
                    Toast.makeText(requireContext(),"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            })*/

        }




    }


