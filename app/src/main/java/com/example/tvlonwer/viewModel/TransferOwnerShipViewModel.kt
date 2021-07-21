package com.example.tvlonwer.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.tvlonwer.model.TransferOwnerShipRequest
import com.google.firebase.firestore.FirebaseFirestore

class TransferOwnerShipViewModel {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var result= MutableLiveData<Boolean>()

    fun register(owner: String, buyer: String, plateNo: String) {
        var data = TransferOwnerShipRequest(owner,buyer,plateNo)
        db.collection("TransferRequests").add(data).addOnSuccessListener {
            result.value = true
        }.addOnFailureListener{
            result.value = false
        }
    }
}