package com.example.tvlonwer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.R
import com.example.tvlonwer.view.ui.home.HomeFragment

class AddKilometers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kilometers)
        val btn=findViewById<Button>(R.id.addDrivenKms)
        Log.d("ADDKM", "onCreate")

        btn.setOnClickListener {
            addDrivenKilometers(it)
        }
    }


    fun addDrivenKilometers(view: View) {
        Log.d("ADDKM", "addDrivenKilometers")
        var kmsToAdd = findViewById<EditText>(R.id.enterKilometers)


        val kms = kmsToAdd.text.toString().toInt()
        Log.d("ADDKM", kms.toString())
        if(CURRENTSELECTEDVEHICLE.getVehicleUser()!=null) {
            val currKms = CURRENTSELECTEDVEHICLE.getCurrentKilometer()
            Log.d("ADDKM", currKms.toString())
            Log.d("ADDKM", CURRENTSELECTEDVEHICLE.getCurrentLicense())
            val total = currKms + kms
            Log.d("ADDKM", total.toString())

            CURRENTSELECTEDVEHICLE.setCurrentKilometers(total)
            Log.d("ADDKM", CURRENTSELECTEDVEHICLE.getCurrentKilometer().toString())
            Toast.makeText(this, "Kilometers Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainScreenActivity::class.java));
        }else{
            Toast.makeText(this, "Kilometers Not Added", Toast.LENGTH_SHORT).show()
        }


    }
}