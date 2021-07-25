package com.example.tvlonwer.viewModel

import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.R
import com.example.tvlonwer.model.Vendor
import com.example.tvlonwer.view.AddAppointment
import com.example.tvlonwer.view.ViewAppointments
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.GeoPoint
import java.sql.Timestamp


class AddAppointmentViewModel : ViewModel() {
    var vendors = MutableLiveData<ArrayList<Vendor>>()
    var db = FirebaseFirestore.getInstance()
    var result = MutableLiveData<Boolean>()

    fun getVendords(){

        var list = ArrayList<Vendor>()
        var col = db.collection("Vendor").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var a = document.data
                    var name : String? = a["name"] as String
                    var address : String? = a["address"] as String
                    var phone : String? = a["phone"] as String
                    var location: GeoPoint? = a["location"] as GeoPoint
                    list.add(Vendor(a["uid"] as String,name,address,phone,
                        LatLng(location!!.latitude,location!!.longitude)
                    ))
                }
                vendors.value = list
            }
    }

    fun addApointment(  approved : Boolean,
                        ownerId : String,
                        time :Timestamp,
                        uservehicle_Id :String,
                        vendor_Id :String){
        val data = hashMapOf(
            "approved" to approved,
            "owner_id" to ownerId,
            "time" to time,
            "uservehicle_id" to uservehicle_Id,
            "vendor_id" to vendor_Id
        )
        var db = FirebaseFirestore.getInstance()
        db.collection("Appointment").add(data).addOnSuccessListener {
                documentReference ->

            result.value = true
        }.addOnFailureListener { e ->

            result.value = false
        }
    }
}