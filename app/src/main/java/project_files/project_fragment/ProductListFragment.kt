package project_files.project_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentProductListBinding
import project_files.MainActivity
import project_files.projectAdapter.ItemListAdapter
import project_files.projectAdapter.ItemListener
import project_files.project_View_Model.ProductView
import project_files.project_model.ProductJSon

class ProductListFragment : Fragment(){
    private val viewModel: ProductView by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductListBinding.inflate(inflater)

        val categoryName = arguments?.getString("NAME")!!
        viewModel.getProductsList(categoryName)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = categoryName.replaceFirstChar { it.uppercaseChar() }
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter = ItemListAdapter("ProductListFragment", ItemListener
        {
            viewModel.onProductClicked(it as ProductJSon)
            findNavController()
                .navigate(R.id.action_productListFragment_to_productFragment)
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}