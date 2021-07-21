package com.example.tvlonwer.view.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvlonwer.CURRENTSELECTEDVEHICLE
import com.example.tvlonwer.model.Part

class SlideshowViewModel : ViewModel() {
    private var partsExpired = ArrayList<Part?>()
    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun getParts():ArrayList<Part?>{
        var parts = CURRENTSELECTEDVEHICLE.getVehicleUser()!!.getVehicle().parts
        for(i in 0 until parts!!.size-1){
            if(parts.get(i)!!.remainingLife.equals("0.0"))
                partsExpired.add(parts!!.get(i))
        }
        return partsExpired
    }
}