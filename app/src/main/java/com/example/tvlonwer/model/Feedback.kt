package com.example.tvlonwer.model

class MonitorComplaint {
    var vendorID:String?= ""
    var ownerID:String?= ""
    var rating:String?= ""
    var description:String?= ""
    var ownerName:String?=""
    var vendorName:String?=""

    constructor(vendorID: String?, ownerID: String?, rating: String?,description:String?,
                ownerName:String?, vendorName:String?) {
        this.vendorID = vendorID
        this.ownerID = ownerID
        this.rating = rating
        this.description=description
        this.ownerName=ownerName
        this.vendorName=vendorName
    }


}