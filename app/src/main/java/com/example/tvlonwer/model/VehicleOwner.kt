package com.example.tvlonwer.model

class VehicleUser {
    private var  plateNo : String = ""
    private var  vehicleUserId: String =""
    private var  kilometers: Int = 0
    private var vehicleId: String=""
    private lateinit var  vehicle : Vehicle

    constructor(
        plateNo: String,
        vehicleUserId: String,
        kilometers: Int,
        vehicleId: String,
        vehicle: Vehicle
    ) {
        this.plateNo = plateNo
        this.vehicleUserId = vehicleUserId
        this.kilometers = kilometers
        this.vehicleId = vehicleId
        this.vehicle = vehicle
    }
}