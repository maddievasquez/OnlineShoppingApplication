package project_files.project_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentCategoryListBinding
import project_files.projectAdapter.ItemListAdapter
import project_files.projectAdapter.ItemListener
import project_files.project_View_Model.CategoryView
import project_files.project_model.CategoryJSon

class CategoryListFragment : Fragment() {

    private val viewModel: CategoryView by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoryListBinding.inflate(inflater)

        viewModel.getCategoriesList()

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerView.adapter =
            ItemListAdapter("CategoryListFragment", ItemListener {
                val categoryName = viewModel.onCategoryClicked(it as CategoryJSon)
                val bundle = bundleOf("NAME" to categoryName)
                findNavController().navigate(
                    R.id.action_viewPagerFragment_to_productListFragment,
                    bundle
                )
            })

        // Inflate the layout for this fragment
        return binding.root
    }
}