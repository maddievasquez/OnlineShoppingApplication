package project_files.project_model


import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class NameJSon (
    val firstname: String,
    val lastname: String
)