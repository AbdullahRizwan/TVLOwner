package com.example.tvlonwer.model

import java.io.Serializable

class Vehicle : Serializable{
    var vehicleId:String ?= ""
    var model:String ?= ""
    var make:String ?= ""
    var year:String ?= ""
    val parts : ArrayList<Part?> ?= ArrayList()

    constructor(vehicleId: String?, model: String?, make: String?, year: String?, _parts: ArrayList<Part>) {
        this.vehicleId = vehicleId
        this.model = model
        this.make = make
        this.year = year
        this.parts?.addAll( _parts)
    }
}