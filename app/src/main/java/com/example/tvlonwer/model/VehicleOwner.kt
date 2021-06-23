package com.example.tvlonwer.model

class  VehicleUser {
    private var  plateNo : String = ""
    private var  vehicleUserId: String =""
    private var  kilometers: Int = 0
    private var vehicleId: String=""
    private var  vehicle : Vehicle

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

    public fun getVehicle(): Vehicle {
        return this.vehicle
    }
    public fun getPlateNo(): String {
        return this.plateNo;
    }
    public fun getKilometers():Int{
        return this.kilometers;
    }
    public fun setKilometers(kms:Int){
        kilometers=kms;
    }
    fun setPlate(no:String){
        plateNo = no
    }


}