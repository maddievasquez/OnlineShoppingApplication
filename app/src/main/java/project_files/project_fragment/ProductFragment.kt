package project_files.project_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentProductBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import project_files.MainActivity
import project_files.project_View_Model.ProductView

class ProductFragment :Fragment() {

    private val viewModel: ProductView by activityViewModels()
    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = viewModel.product.value?.let {
                it.category.replaceFirstChar { char -> char.uppercaseChar() }
            }
        }

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            var itemNumber = numberItemsSelected.text.toString().toInt()

            buttonRemove.setOnClickListener {
                if (itemNumber > 0)
                    numberItemsSelected.text = (--itemNumber).toString()
                if (itemNumber == 0) {
                    buttonRemove.isClickable = false
                    buttonAddToCart.isClickable = false
                }
            }

            buttonAdd.setOnClickListener {
                numberItemsSelected.text = (++itemNumber).toString()
                if (itemNumber > 0) {
                    buttonRemove.isClickable = true
                    buttonAddToCart.isClickable = true
                }
            }

            buttonAddToCart.setOnClickListener {
                if (itemNumber > 0)
                    Snackbar.make(view, "$itemNumber item(s) added to your cart!",
                        BaseTransientBottomBar.LENGTH_LONG
                    )
                        .show()
                if (itemNumber == 0)
                    buttonAddToCart.isClickable = false
            }
        }
    }

}