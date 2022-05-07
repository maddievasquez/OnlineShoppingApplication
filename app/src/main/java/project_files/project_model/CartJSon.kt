package project_files.project_model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CartJSon(
    override var id: Int,
    val userId: Int,
    var date: String,
    var products: List<CartProductJson>
) : DataJSon(id = -1, lastModified = null, type = "cart"), Parcelable