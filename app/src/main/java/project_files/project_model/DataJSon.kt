package project_files.project_model

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
open class DataJSon (
open val id: Int = 1,
open val lastModified: LocalDateTime?,
open val type: String
)
