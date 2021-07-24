package com.example.tvlonwer.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.tvlonwer.model.Vendor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

class ShowVendorsViewModel{
    val db = FirebaseFirestore.getInstance()
    var data = MutableLiveData<ArrayList<Vendor>>()

    fun getVendords(){
        var list = ArrayList<Vendor>()
        var col = db.collection("Vendor").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var a = document.data
                    var name : String? = a["name"] as String
                    var address : String? = a["address"] as String
                    var phone : String? = a["phone"] as String
                    var location:GeoPoint? = a["location"] as GeoPoint
                    list.add(Vendor(a["uid"] as String,name,address,phone,
                        LatLng(location!!.latitude,location!!.longitude)
                    ))
                }
                data.value = list
            }

    }

}