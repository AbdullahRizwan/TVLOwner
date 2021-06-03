package com.example.tvlonwer.model

import java.io.Serializable


class Part : Serializable {

    var partId: String? = ""
    var name: String? = ""
    var type: String? = ""
    var life: String? = ""
    var remainingLife :String?=""
    var description: String? = ""

    constructor(
        partId: String?,
        name: String?,
        type: String?,
        life: String?,
        remainingLife: String?,
        description: String?
    ) {
        this.partId = partId
        this.name = name
        this.type = type
        this.life = life
        this.remainingLife = remainingLife
        this.description = description
    }

}
