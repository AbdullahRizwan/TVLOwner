package com.example.tvlonwer.model

import com.google.android.gms.maps.model.LatLng

data class Vendor(
    val id: String?,
    val name: String?,
    val address: String?,
    val phone: String?,
    val location: LatLng?
)

