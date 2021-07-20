package com.example.tvlonwer.view.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Owner
import com.example.tvlonwer.model.Part
import com.example.tvlonwer.model.Vehicle
import com.example.tvlonwer.model.VehicleUser
import com.example.tvlonwer.view.Activity_Select_Current_Vehicle
import com.example.tvlonwer.view.MainScreenActivity
import com.example.tvlonwer.view.TransferOwnership
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var progessBar:ProgressBar
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
        progessBar = root.findViewById(R.id.pbar)

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
             if (kmsText != null) {
                 textView.text = kmsText
                 kms = kmsText.toDouble()
             }
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
            transferOwnership()
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
                                                  which ->
                    manual_kms = input.text.toString()
                    updateKms(manual_kms.toFloat())
                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
        }

        getVehicleFromPreference()

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainScreenActivity?)!!
            .setActionBarTitle("Home")
    }

    private fun getVehicleFromPreference() {
        progessBar.visibility= View.VISIBLE
        val preferences = this.requireActivity().getSharedPreferences("MyPref", MODE_PRIVATE)
        val id: String? = preferences.getString(getString(R.string.cvhcl), "empty")
        if(!(id.equals("empty", ignoreCase = true))){
            CURRENTSELECTEDVEHICLE.setCurrentPlate(id.toString())
            var currentUser : String? = FirebaseAuth.getInstance().uid
            val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            var col = db.collection("UserVehicle").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        var a = document.data
                        val vehicle = a["Vehicle"] as Map<String, *>
                        if(a["uid"]?.equals(currentUser) == true){
                            var lisenceNumber = a["lisenceNumber"] as String
                            var uid = a["uid"] as String
                            var vehicleKilometers = a["vehicleKilometer"].toString().toFloat() as Float
                            var vehicleId = vehicle["vehicleId"] as String
                            var vehicleModel = vehicle["model"].toString()
                            var vehicleMake = vehicle["make"].toString()
                            var vehicleYear = vehicle["year"].toString()
                            var parts : ArrayList<HashMap<String, String>> = vehicle["parts"] as ArrayList<HashMap<String, String>>
                            var partsofVehicle: ArrayList<Part> = ArrayList()
                            for(map in parts){
                                partsofVehicle.add(
                                    Part(
                                        map["partId"] as String,
                                        map["name"] as String,
                                        map["type"] as String,
                                        map["life"] as String,
                                        map["remainingLife"] as String,
                                        map["description"] as String,
                                    )
                                )
                            }
                            if(vehicleId.equals(id)){
                                CURRENTSELECTEDVEHICLE.setCurrentVehicle(
                                    VehicleUser(
                                        lisenceNumber, uid, vehicleKilometers, vehicleId,
                                        Vehicle(
                                            vehicleId,
                                            vehicleModel,
                                            vehicleMake,
                                            vehicleYear,
                                            partsofVehicle
                                        )
                                    )
                                )
                                this.textView.setText(
                                    CURRENTSELECTEDVEHICLE.getCurrentKilometer().toString()
                                )
                                this.plateView.setText(CURRENTSELECTEDVEHICLE.getCurrentLicense())
                                progessBar.visibility= View.GONE
                            }

                        }
                    }

                }
                .addOnFailureListener { exception ->

                }

        }



    }

    private fun updateKms(manual_kms: Float) {
        var prevKms = CURRENTSELECTEDVEHICLE.getVehicleUser()?.getKilometers()
        var prevKm = prevKms?.toFloat()
        var currentKms = manual_kms
        var diff = prevKm!! - currentKms
        textView.text = manual_kms.toString()
        CURRENTSELECTEDVEHICLE.getVehicleUser()?.setKilometers(currentKms)
        var vehicle = CURRENTSELECTEDVEHICLE.getVehicleUser()?.getVehicle()
        var parts = vehicle!!.parts
        if (parts != null) {
            for(i in 0..parts?.size-1){
                var partLife = parts[i]?.remainingLife?.toFloat()
                partLife = partLife?.plus((diff.toFloat()))
                if(partLife!!.compareTo(0) > 0 ){
                    parts[i]?.remainingLife = partLife.toString()
                }
                else{
                    parts[i]?.remainingLife = ""+0.0
                }

            }
        }
        var change = currentKms - prevKm!!
        var db = FirebaseFirestore.getInstance()
        var table = db.collection("UserVehicle").get().addOnSuccessListener { result ->
            for (documents in result) {
                var a = documents.data
                if(a["uid"]?.equals(Owner.uid) == true && a["lisenceNumber"]?.equals(
                        CURRENTSELECTEDVEHICLE?.getCurrentLicense()
                    ) == true){
                    db.collection("UserVehicle").document(documents.id).update(mapOf("vehicleKilometer" to manual_kms))
                    db.collection("UserVehicle").document(documents.id).update(
                        mapOf(
                            "Vehicle" to CURRENTSELECTEDVEHICLE.getVehicleUser()!!.getVehicle()
                        )
                    )
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

    private fun transferOwnership(){

        val fragmentManager: FragmentManager? = fragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, TransferOwnership())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
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
            kms = CURRENTSELECTEDVEHICLE.getCurrentKilometer().toDouble()
            Toast.makeText(mContext, "" + kms, Toast.LENGTH_SHORT).show()
            var newKms= (distance/1000)
            kms +=  newKms

            //CURRENTSELECTEDVEHICLE.setCurrentKilometers(kms.toFloat())
            kilometersView.text = ""+kms.toFloat()
            updateKms(kms.toFloat())
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}