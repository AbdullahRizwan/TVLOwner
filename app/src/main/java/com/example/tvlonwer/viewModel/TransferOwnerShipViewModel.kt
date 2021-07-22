package com.example.tvlonwer.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.tvlonwer.model.Owner
import com.example.tvlonwer.model.TransferOwnerShipRequest
import com.example.tvlonwer.model.VehicleUser
import com.google.firebase.firestore.FirebaseFirestore

class TransferOwnerShipViewModel {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var result= MutableLiveData<Boolean>()
    var platesResult= MutableLiveData<ArrayList<String>>()
    fun register(owner: String, buyer: String, plateNo: String) {
        var data = TransferOwnerShipRequest(owner,buyer,plateNo)
        db.collection("TransferRequests").add(data).addOnSuccessListener {
            result.value = true
        }.addOnFailureListener{
            result.value = false
        }
    }

    fun getPlates(){
        var list = ArrayList<String>()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var col = db.collection("UserVehicle").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var a = document.data
                    val vehicle = a["Vehicle"] as Map<String, *>
                    if (a["uid"]?.equals(Owner.uid) == true) {
                        var lisenceNumber = a["lisenceNumber"] as String
                        list.add(lisenceNumber)

                    }
                }
                platesResult.value = list
            }

    }

}