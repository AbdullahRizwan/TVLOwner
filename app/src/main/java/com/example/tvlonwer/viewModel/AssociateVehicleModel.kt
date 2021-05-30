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

class AssociateVehicleModel  : ViewModel()  {
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
                    list.add(
                        Vehicle(
                            document.id.toString(),
                            a["model"] as String?,
                            a["make"] as String?,
                            a["year"] as String?,
                            a["parts"] as ArrayList<Part>
                        )
                    )
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