package project_files.project_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentAboutBinding
import project_files.MainActivity
import project_files.project_constant.BASE_URL
import project_files.project_constant.PROJECTS_GITHUB_LINK

class AboutAppFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = "About"
        }

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            datasource.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URL)))
            }

            github.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PROJECTS_GITHUB_LINK)))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}