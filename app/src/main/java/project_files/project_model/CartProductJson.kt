package project_files.project_model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
@Parcelize
@JsonClass(generateAdapter = true)
data class CartProductJson (
    val productId: Int,
    var quantity: Int
) : DataJSon(id = productId, lastModified = null, type = "cartProduct"),
    Parcelable