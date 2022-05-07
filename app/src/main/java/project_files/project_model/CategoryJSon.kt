package project_files.project_model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class CategoryJSon(
    override val id: Int,
    val name: String
) : DataJSon(id = -1, lastModified = null, type = "category")