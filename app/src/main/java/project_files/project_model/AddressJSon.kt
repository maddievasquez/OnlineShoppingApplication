package project_files.project_model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressJSon(
    var city: String?,
    var street: String?,
    var number: Int?,
    var zipcode: String?,
    var geolocation: Geolocation?
)