package com.example.tvlonwer.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvlonwer.model.Part
import com.example.tvlonwer.model.Vehicle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.tvlonwer.model.Result

class AssociateVehicleViewModel  : ViewModel()  {
    var data = MutableLiveData<ArrayList<Vehicle>>()
    private val _result = MutableLiveData<Result<String>>()
    val result : LiveData<Result<String>> = _result

    init {
        getData()
    }

    private fun getData() {
        var list = ArrayList<Vehicle>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var col = db.collection("CompleteVehicle").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var a = document.data
                    var make : String? = a["make"] as String
                    var model : String? = a["model"] as String
                    var year : String? = a["year"] as String
                    var parts : ArrayList<HashMap<String,String>> = a["parts"] as ArrayList<HashMap<String, String>>
                    var partsofVehicle: ArrayList<Part> = ArrayList()
                    for(map in parts){
                        partsofVehicle.add(Part(
                            map["partId"] as String,
                            map["name"] as String,
                            map["type"] as String,
                            map["life"] as String,
                            "0.0",
                            map["description"] as String,

                        ))
                    }
                    list.add(Vehicle( document.id.toString(),model,make,year,partsofVehicle))

                }
                data.value = list
            }
            .addOnFailureListener { exception ->

            }
    }

    fun addVehicle(mText: String,lisenceNo: String, vehicle: Vehicle) {
        val uid: String = FirebaseAuth.getInstance().currentUser.uid.toString()
        val data = hashMapOf(
            "uid" to uid,
            "lisenceNumber" to lisenceNo,
            "vehicleKilometer" to mText,
            "Vehicle" to vehicle
        )

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("UserVehicle").add(data).addOnSuccessListener { documentReference ->
            _result.value = Result.Success<String>(documentReference.toString())
            Log.d("Result", documentReference.toString())
        }.addOnFailureListener { e ->
            _result.value = Result.Error(e)
            Log.d("Result", e.toString())
        }
    }

}