package com.example.tvlonwer.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.model.Appointment
import com.example.tvlonwer.model.Owner
import com.example.tvlonwer.model.Vendor
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class ViewAppointmentViewModel {
    var appointments = MutableLiveData<ArrayList<Appointment>>()
    var vendors = MutableLiveData<ArrayList<Vendor>>()
    var db = FirebaseFirestore.getInstance()
    var vendorIDs : String = ""

    fun getAppointments(){
        var lst = ArrayList<Appointment>()
        db.collection("Appointment")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        var a = document.data
                        if(CURRENTSELECTEDVEHICLE.getVehicleUser()!!.getId().contains(a["owner_id"] as String)) {
                            var owner_id: String = a["owner_id"] as String
                            var uservehicle_id: String = a["uservehicle_id"] as String
                            var vendor_id: String = a["vendor_id"] as String
                            vendorIDs+=vendor_id
                            vendor_id+=" "
                            var time: Timestamp = a["time"] as Timestamp
                            lst.add(Appointment(owner_id, uservehicle_id, vendor_id, time))
                        }
                    }
                } else {
                    Log.w("TAG", "Error getting documents.", task.exception)
                }
                appointments.value = lst
            }
    }

    fun getVendors(appointments: ArrayList<Appointment>){
        var list = ArrayList<Vendor>()
        var col = db.collection("Vendor").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var a = document.data
                    if(vendorIDs.contains(a["uid"] as String)) {
                        var name: String? = a["name"] as String
                        var address: String? = a["address"] as String
                        var phone: String? = a["phone"] as String
                        var location: GeoPoint? = a["location"] as GeoPoint
                        list.add(
                            Vendor(
                                a["uid"] as String, name, address, phone,
                                LatLng(location!!.latitude, location!!.longitude)
                            )
                        )
                    }
                }
                vendors.value = list
            }
    }
}