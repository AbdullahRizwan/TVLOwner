package com.example.tvlonwer.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvlonwer.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CurrentVehicleViewModel : ViewModel()  {
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
                    if(a["uid"]?.equals(Owner.uid) == true){
                        var lisenceNumber = a["lisenceNumber"] as String
                        var uid = a["uid"] as String
                        var vehicleKilometers = a["vehicleKilometer"].toString().toFloat() as Float
                        var vehicleId = vehicle["vehicleId"] as String
                        var vehicleModel = vehicle["model"].toString()
                        var vehicleMake = vehicle["make"].toString()
                        var vehicleYear = vehicle["year"].toString()
                        var parts : ArrayList<HashMap<String,String>> = vehicle["parts"] as ArrayList<HashMap<String, String>>
                        var partsofVehicle: ArrayList<Part> = ArrayList()
                        for(map in parts){
                            partsofVehicle.add(Part(map["partId"] as String,
                                map["name"] as String,
                                map["type"] as String,
                                map["life"] as String,
                                map["remainingLife"]as String,
                                map["description"] as String,
                            ))
                        }
                        list.add(VehicleUser(lisenceNumber,uid,vehicleKilometers,vehicleId,
                            Vehicle(vehicleId,vehicleModel,vehicleMake,vehicleYear,partsofVehicle)))
                    }
                }
                data.value = list
            }
            .addOnFailureListener { exception ->

            }
    }


   /*fun addVehicle(mText: String,lisenceNo: String, vehicle: Vehicle) {
     //   val uid: String = FirebaseAuth.getInstance().currentUser.uid.toString()
        val data = hashMapOf(
            "lisenceNumber" to lisenceNo,
            "vehicleKilometer" to mText

        )

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("UserVehicle").add(data).addOnSuccessListener{ documentReference ->
            _result.value = Result.Success<String>(documentReference.toString())
            Log.d("Result", documentReference.toString())
        }.addOnFailureListener { e ->
            _result.value = Result.Error(e)
            Log.d("Result", e.toString())
        }
    }*/

}