package com.example.tvlonwer.model

import android.text.Editable

class TransferOwnerShipRequest {
    var ownerEmail: String? = ""
    var buyerEmail: String? = ""
    var plateNo: String? = ""
    var status: String? = ""

    constructor(ownerEmail: String?, buyerEmail: String, plateNo: String) {
        this.ownerEmail = ownerEmail
        this.buyerEmail = buyerEmail
        this.plateNo = plateNo
        status = "Pendind"
    }
}