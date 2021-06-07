package com.example.tvlonwer.view.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Owner
import com.example.tvlonwer.view.Activity_Select_Current_Vehicle
import com.example.tvlonwer.view.MainScreenActivity
import com.example.tvlonwer.view.TransferOwnership
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var startLocation: Location
    private lateinit var endLocation: Location
    private var locationManager: LocationManager? = null
    private var mContext: Context? = null
    private var kms : Double = 0.0
    private lateinit var kilometersView:TextView
    private var firstTime: Boolean = true
    private var manual_kms = ""
    private lateinit var textView: TextView
    private lateinit var plateView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        textView = root.findViewById(R.id.kms)
        plateView = root.findViewById(R.id.plate_no)
        kilometersView = root.findViewById(R.id.kms)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {


        })
        mContext = this.activity
        locationManager = this.activity?.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        locationManager = (mContext as MainScreenActivity).getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        locationManager = mContext?.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

        isLocationEnabled()

        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                200
            )
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                200
            )
        }

        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            200.toLong(),
            5.0.toFloat(),
            locationListenerGPS
        )
            if(CURRENTSELECTEDVEHICLE.getVehicleUser()!=null) {
             val kmsText = CURRENTSELECTEDVEHICLE.getCurrentKilometer().toString()
             if (kmsText != null)
                 textView.text = kmsText
             else
                 textView.text = "0 " +kms
             val licenseText = CURRENTSELECTEDVEHICLE.getCurrentLicense()
             if (licenseText != null)
                 plateView.text = licenseText
             else
                 plateView.text = "0"

        }else {
                textView.text = "NOT SET"
                plateView.text = "NOT SET"
            }
        val ownershipBtn: Button
        ownershipBtn=root.findViewById(R.id.transferOwnership)
        ownershipBtn.setOnClickListener{
            startActivity(Intent(root.context, TransferOwnership::class.java))
        }
        val selectVehicleBtn:Button
        selectVehicleBtn=root.findViewById(R.id.selectVehicle)
        selectVehicleBtn.setOnClickListener {
            startActivity(Intent(root.context, Activity_Select_Current_Vehicle::class.java))
        }
        val selectKilometersBtn:Button
        selectKilometersBtn=root.findViewById(R.id.addKilometer)
        selectKilometersBtn.setOnClickListener {
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this.activity)
            builder.setTitle("Wrirte kilometers you see in vehicle Meter")

            val input = EditText(this.activity)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog,
                                                  which -> manual_kms = input.text.toString()
                    updateKms()
                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
        }

        return root
    }

    private fun updateKms() {
        var prevKms = CURRENTSELECTEDVEHICLE.getVehicleUser()?.getKilometers()
        var prevKm = prevKms?.toInt()
        var currentKms = manual_kms?.toInt()
        textView.text = manual_kms
        CURRENTSELECTEDVEHICLE.getVehicleUser()?.setKilometers(currentKms)
        var change = currentKms - prevKm!!
        var db = FirebaseFirestore.getInstance()
        var table = db.collection("UserVehicle").get().addOnSuccessListener { result ->
            for (documents in result) {
                var a = documents.data
                if(a["uid"]?.equals(Owner.uid) == true){
                   db.collection("UserVehicle").document(documents.id).update(mapOf("vehicleKilometer" to manual_kms))
                }
            }
        }


    }


    private fun isLocationEnabled() {
        if (!locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!) {
            val alertDialog: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this.requireContext())
            alertDialog.setTitle("Enable Location")
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.")
            alertDialog.setPositiveButton("Location Settings",
                DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                })
            alertDialog.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            val alert: android.app.AlertDialog? = alertDialog.create()
            if (alert != null) {
                alert.show()
            }
        }
    }

    var locationListenerGPS: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if(firstTime){
                endLocation =location
                firstTime =  false
                return
            }
            startLocation = endLocation
            endLocation =location

            var distance = startLocation.distanceTo(endLocation)
            kms +=(distance/1000)
            kilometersView.text = ""+kms
            val msg = "Distance: " + distance
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}