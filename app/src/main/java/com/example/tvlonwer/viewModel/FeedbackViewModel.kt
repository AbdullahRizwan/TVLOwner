package com.example.tvlonwer.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.tvlonwer.model.Vendor
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import java.sql.Timestamp

class FeedbackViewModel {

    var db = FirebaseFirestore.getInstance()
    var result = MutableLiveData<Boolean>()
    private lateinit var auth: FirebaseAuth

    fun addFeedback(description:String?,rating:String?,
    vendor_Id:String,vendorName:String?){
        auth = FirebaseAuth.getInstance()
        val uid: String = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        db.collection("Owner").document(auth.currentUser?.uid!!).get().addOnSuccessListener { res->
            if(res["uid"]==uid){
                var ownerName:String=res["name"].toString()
                var ownerId:String=res["uid"].toString()
                val data = hashMapOf(
                    "description" to description,
                    "owner_id" to ownerId,
                    "ownerName" to ownerName,
                    "rating" to rating,
                    "vendorName" to vendorName,
                    "vendor_id" to vendor_Id
                )
                var db = FirebaseFirestore.getInstance()
                db.collection("Feedback").add(data).addOnSuccessListener {
                        documentReference ->

                    result.value = true
                }.addOnFailureListener { e ->
                    result.value = false
                }
            }
        }
        /*db.collection("Owner").get().addOnSuccessListener { result->

        }*/

    }
}