package project_files.projectAdapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dorset_22773_madelin_vasquez.project_files.R

import project_files.project_model.DataJSon
import project_files.project_network.OnlineShoppingApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataJSon>?) {
    val adapter = recyclerView.adapter as ItemListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl", "placeholder")
fun bindImageUrl(imageView: ImageView, url: String?, placeholder: Drawable) {
    if (url != null) {
        imageView.load(url) {
            crossfade(true)
            placeholder(placeholder)
        }
    } else {
        imageView.load(placeholder)
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: OnlineShoppingApiStatus?) {
    when (status) {
        OnlineShoppingApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        OnlineShoppingApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        OnlineShoppingApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
    }
}