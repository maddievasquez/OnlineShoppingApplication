package project_files.project_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dorset_22773_madelin_vasquez.project_files.R
import com.dorset_22773_madelin_vasquez.project_files.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import project_files.MainActivity
import project_files.project_model.SessionObject
import project_files.project_model.SessionObject.token
import project_files.project_network.OnlineShoppingApi
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as MainActivity).supportActionBar?.apply {
            title = resources.getString(R.string.app_name)
            subtitle = "Sign In"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref =
            activity?.getSharedPreferences("sessionData", Context.MODE_PRIVATE) ?: return

//        Uncomment following line to reset sharedPreferences
//        sharedPref.edit().clear().apply()

        if (sharedPref.getInt("userId", -1) > 0 && sharedPref.contains("token")) {
            SessionObject.apply {
                userId = sharedPref.getInt("userId", -1)
                token = sharedPref.getString("token", null)!!
            }
            findNavController().navigate(R.id.action_signInFragment_to_viewPagerFragment)
        } else {

//            Uncomment following two lines to set up test driver
//            binding.username.setText("johnd", TextView.BufferType.EDITABLE)
//            binding.password.setText("m38rmF$", TextView.BufferType.EDITABLE)

            binding.apply {
                buttonSignIn.setOnClickListener {
                    lifecycleScope.launch {
                        try {
                            OnlineShoppingApi.retrofitService.signIn(
                                binding.username.text.toString(),
                                binding.password.text.toString()
                            ).also {
                                token = it["token"].toString()

                                val userId = OnlineShoppingApi.retrofitService.getUsers()
                                    .find { user -> user.username == binding.username.text.toString() }?.id

                                with(sharedPref.edit()) {
                                    putInt("userId", userId!!)
                                    putString("token", it["token"])
                                    apply()
                                }

                                SessionObject.userId = userId!!

                                Log.d("SignIn", SessionObject.userId.toString())
                                Log.d("SignIn", token)

                                findNavController().navigate(R.id.action_signInFragment_to_viewPagerFragment)
                            }
                        } catch (e: Exception) {
                            Log.d("SignIn catch", e.toString())
                            Snackbar.make(
                                view,
                                "Login failed! Please, verify that both Username and Password are correct!",
                                BaseTransientBottomBar.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                buttonSignUp.setOnClickListener {
                    findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
                }
            }

            activity?.onBackPressedDispatcher?.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        activity?.finish()
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}