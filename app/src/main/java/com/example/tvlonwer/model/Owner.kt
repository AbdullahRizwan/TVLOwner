package com.example.tvlonwer.model

import java.io.Serializable

object Owner : Serializable {
    var displayName:String = ""
    private var email:String = ""
    var cnicNumber:String = ""
    var phone:String = ""
    var uid:String = ""

    fun setValues(name:String, _email:String, cnic:String, _phone:String,_uid:String){
        this.displayName = name
        this.email = _email
        this.cnicNumber = cnic
        this.phone = _phone
        this.uid = _uid
    }
    fun email():String{
        return email
    }
}