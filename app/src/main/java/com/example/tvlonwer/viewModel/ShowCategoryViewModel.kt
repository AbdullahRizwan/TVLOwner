package com.example.tvlonwer.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.tvlonwer.model.Vehicle
import com.google.firebase.firestore.FirebaseFirestore

class ShowCategoryViewModel {
    var categories = MutableLiveData<ArrayList<String>>()
    var categoryString = ""

    fun getCategory(vehicle:Vehicle){
        var parts = vehicle.parts
        var list =ArrayList<String>()
        if (parts != null) {
            for(p in parts){
                if(!categoryString.contains(p!!.type.toString())){
                    categoryString+=p.type
                    categoryString+=" "
                    list.add(p!!.type.toString())
                }
            }
        }
        categories.value = list
    }

}