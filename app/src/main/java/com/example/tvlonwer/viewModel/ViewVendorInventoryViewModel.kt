package com.example.tvlonwer.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvlonwer.model.Part
import com.example.tvlonwer.model.VendorParts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewVendorInventoryViewModel: ViewModel() {
    var data = MutableLiveData<List<VendorParts>>()

    fun loadParts(uid:String){
        var db = FirebaseFirestore.getInstance()
        db.collection("Vendor").document(uid).get().addOnSuccessListener { res ->
            if(res["inventory"] != null) {

                val parts = mutableListOf<VendorParts>()
                val partsIds = mutableListOf<String>()

                @Suppress("UNCHECKED_CAST")
                val partsInfo = res["inventory"] as ArrayList<HashMap<String, Any>>?
                if(partsInfo != null) {
                    for (part in partsInfo) {
                        partsIds.add(part["id"].toString())
                        parts.add(VendorParts(part["id"] as String?, quantity = part["quantity"] as Number?, price = part["price"] as Number?))
                    }
                    val idRef = com.google.firebase.firestore.FieldPath.documentId()
                    db.collection("Part").whereIn(idRef, partsIds).get().addOnSuccessListener { partSnapshot ->
                        for (doc in partSnapshot){
                            val index = partsIds.indexOf(doc.id)
                            if(index != -1)
                                parts[index] = VendorParts(doc.id, doc["name"] as String?, doc["description"] as String?, doc["life"] as String?, doc["type"] as String?, parts[index].price, parts[index].quantity)

                        }
                        data.value = parts
                    }.addOnFailureListener {
                        data.value = parts
                    }
                }
            }
        }.addOnFailureListener{
            Log.d("ERROR", it.toString())
            data.value = null
        }
    }
}