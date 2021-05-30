package com.example.tvlonwer.view.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvlonwer.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GalleryViewModel : ViewModel() {
    var data = MutableLiveData<ArrayList<VehicleUser>>()
    private val _result = MutableLiveData<Result<String>>()
    val result : LiveData<Result<String>> = _result

    init {
        getData()
    }

    private fun getData() {
        var list = ArrayList<VehicleUser>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var col = db.collection("UserVehicle").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var a = document.data
                    val vehicle = a["Vehicle"] as Map<String, *>
                    if(a["uid"]?.equals(Owner.uid) == true)
                    list.add(
                        VehicleUser(
                            a["lisenceNumber"] as String,
                            a["uid"] as String,
                            a["vehicleKilometer"].toString().toInt() as Int,
                            vehicle["vehicleId"] as String,
                            Vehicle(vehicle["vehicleID"].toString(),
                                vehicle["model"].toString(),
                                vehicle["make"].toString(),
                                vehicle["year"].toString(),
                                vehicle["parts"] as ArrayList<Part>)
                        )
                    )
                }
                data.value = list
            }
            .addOnFailureListener { exception ->

            }
    }

}