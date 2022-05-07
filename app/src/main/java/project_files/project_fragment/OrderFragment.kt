package project_files.project_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentOrderBinding
import project_files.MainActivity
import project_files.projectAdapter.ItemListAdapter
import project_files.projectAdapter.ItemListener
import project_files.project_View_Model.OrderView
import project_files.project_model.CartJSon
import project_files.project_model.ProductJSon

class OrderFragment : Fragment(){
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderView by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = arguments?.getParcelable<CartJSon>("order")!!
        viewModel.onCheckOut(order)
        viewModel.getOrderProductsList(order)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = "Order #${viewModel.order.value?.id}"
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = ItemListAdapter("OrderFragment", ItemListener
        {
            viewModel.onProductClicked(it as ProductJSon)
            findNavController().navigate(R.id.action_orderFragment_to_orderProductFragment)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}