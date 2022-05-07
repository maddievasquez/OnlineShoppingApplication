package project_files.project_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import project_files.MainActivity

class ViewPageFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = "Home"
        }

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 4
            override fun createFragment(position: Int): Fragment =
                when (position) {
                    0 -> CategoryListFragment()
                    1 -> CartFragment()
                    2 -> ListOrderFragment()
                    else -> UserFragment()
                }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Shop"
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_store_24, null)
                }
                1 -> {
                    tab.text = "Cart"
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_shopping_cart_checkout, null)
                }
                2 -> {
                    tab.text = "Orders"
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_bullet_list_order, null)
                }
                3 -> {
                    tab.text = "User"
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_acount_user, null)
                }
            }
        }.attach()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.viewPager.currentItem == 0)
                        activity?.finish()
                    else
                        binding.viewPager.currentItem--
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}