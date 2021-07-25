package com.example.tvlonwer.model

import com.google.firebase.Timestamp
import java.io.Serializable

class Appointment : Serializable {

    var owner_id: String = ""
    var uservehcile_id: String = ""
    var vendor_id: String = ""
    lateinit var timestamp:Timestamp

    constructor(owner_id: String, uservehcile_id: String, vendor_id: String, timestamp: Timestamp) {
        this.owner_id = owner_id
        this.uservehcile_id = uservehcile_id
        this.vendor_id = vendor_id
        this.timestamp = timestamp
    }
}