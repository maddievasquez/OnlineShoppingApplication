package project_files.project_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentOrderListBinding
import project_files.projectAdapter.ItemListAdapter
import project_files.projectAdapter.ItemListener
import project_files.project_View_Model.OrderView
import project_files.project_model.CartJSon


class ListOrderFragment : Fragment() {
    private val viewModel: OrderView by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOrderListBinding.inflate(inflater)

        viewModel.getOrdersList()

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        ItemListAdapter("OrderListFragment", ItemListener
        {
            val order = viewModel.onOrderClicked(it as CartJSon)
            val bundle = bundleOf("order" to order)
            findNavController()
                .navigate(R.id.action_viewPagerFragment_to_orderFragment, bundle)
        }).also { binding.recyclerView.adapter = it }

        // Inflate the layout for this fragment
        return binding.root
    }
}