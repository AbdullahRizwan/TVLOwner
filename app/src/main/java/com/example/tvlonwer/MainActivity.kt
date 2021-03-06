package com.example.tvlonwer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tvlonwer.model.VehicleUser
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

object  CURRENTSELECTEDVEHICLE
{
    private var currentVehicleUser: VehicleUser? =null
    private var notified = false

    fun isNotified():Boolean{
        return notified;
    }

    fun notified(){
        notified = true
    }

    init
    {
        println("Singleton class invoked.")
    }
    fun getVehicleUser():VehicleUser?{
        return currentVehicleUser
    }
    fun setCurrentVehicle(vehicle: VehicleUser){
        currentVehicleUser=vehicle
    }
    fun getCurrentKilometer() :Float
    {
        return currentVehicleUser!!.getKilometers()
    }
    fun getCurrentLicense() : String
    {
        return currentVehicleUser!!.getPlateNo()
    }
    fun setCurrentPlate(plateNo: String){
        currentVehicleUser?.setPlate(plateNo)
    }
    fun setCurrentKilometers(kilometers:Float){
        currentVehicleUser!!.setKilometers(kilometers)
    }
}
