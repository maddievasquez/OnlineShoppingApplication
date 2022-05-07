package project_files.project_model

import com.squareup.moshi.JsonClass
import project_files.project_constant.RANDOM_PROFILE_PICTURE_URL


@JsonClass(generateAdapter = true)
data class UserJSon(
    override var id: Int,
    var email: String?,
    var username: String?,
    var password: String?,
    var name: NameJSon?,
    var address: AddressJSon?,
    var phone: String?,
    var image: String = RANDOM_PROFILE_PICTURE_URL
) : DataJSon(id = -1, lastModified = null, type = "user")