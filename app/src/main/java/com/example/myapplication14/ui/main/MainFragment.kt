package com.example.myapplication14.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication14.R
import com.example.myapplication14.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

private const val USER_MODEL = "userModel"

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private var userModel: UserModel? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        savedInstanceState?.let {
            userModel = it.getParcelable(USER_MODEL)
        }

        if (userModel == null) {
            lifecycleScope.launch {
                try {
                    userModel = viewModel.getJson()
                    if (this@MainFragment.userModel != null) {
                        updateUi(userModel!!)
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } else {
            updateUi(userModel!!)
        }

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                try {
                    userModel = viewModel.getJson()
                    if (this@MainFragment.userModel != null) {
                        updateUi(userModel!!)
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (userModel != null) outState.putParcelable(USER_MODEL, userModel)
        super.onSaveInstanceState(outState)
    }

    private fun updateUi(userModel: UserModel) {
        binding.nameField.text = getString(R.string.user_name, userModel.firstName, userModel.lastName)
        binding.emailField.text = getString(R.string.email_string, userModel.emailString)
        Glide.with(this).load(userModel.photoUrl).fitCenter().into(binding.imageView)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
