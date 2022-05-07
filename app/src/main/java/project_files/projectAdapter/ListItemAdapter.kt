package project_files.projectAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.RecyclerViewItemBinding
import project_files.project_model.DataJSon

class ItemListAdapter(private val callerTag: String, private val clickListener: ItemListener) :
    ListAdapter<DataJSon, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(
        var binding: RecyclerViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ItemListener, item: DataJSon) {
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataJSon>() {

        override fun areItemsTheSame(oldItem: DataJSon, newItem: DataJSon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataJSon, newItem: DataJSon): Boolean {
            return oldItem.lastModified == newItem.lastModified
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("Adapt", item.toString())
        holder.bind(clickListener, item)

        holder.binding.itemStub.viewStub?.layoutResource =
            when (callerTag) {
                "CategoryListFragment" -> R.layout.recycler_view_item_category_list
                "ProductListFragment" -> R.layout.recycler_view_item_product_list
                "CartFragment" -> R.layout.recycler_view_item_cart_product_list
                "OrderListFragment" -> R.layout.recycler_view_item_order_list
                else -> R.layout.recycler_view_item_cart_product_list
            }

        holder.binding.itemStub.viewStub?.inflate()
    }
}

class ItemListener(val clickListener: (item: DataJSon) -> Unit) {
    fun onClick(item: DataJSon) {
        return clickListener(item)
    }
}