package com.example.tvlonwer.model


class Part {
    var partId: String? = ""
    var name: String? = ""
    var type: String? = ""
    var life: String? = ""
    var description: String? = ""

    constructor(
        partId: String?,
        name: String?,
        type: String?,
        life: String?,
        description: String?
    ) {
        this.partId = partId
        this.name = name
        this.type = type
        this.life = life
        this.description = description
    }
}
