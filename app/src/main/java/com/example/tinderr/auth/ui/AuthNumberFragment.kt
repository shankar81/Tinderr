package com.example.tinderr.auth.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.auth.AuthViewModel
import com.example.tinderr.auth.models.LoginBody
import com.example.tinderr.databinding.FragmentAuthNumberBinding
import com.example.tinderr.models.CountryCode
import kotlinx.coroutines.*
import java.io.Serializable

private const val TAG = "AuthNumberFragment"

interface Callbacks : Serializable {
    fun onSelectExt(countryCode: CountryCode)
}

class AuthNumberFragment : Fragment(), Callbacks {

    private var _binding: FragmentAuthNumberBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    private val coroutineScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAuthNumberBinding.inflate(inflater)

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.extDropdown.setText(authViewModel.selectedExt)
        binding.button.updateButton(authViewModel.buttonEnabled)

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                authViewModel.buttonEnabled = s?.length == 10
                binding.button.updateButton(authViewModel.buttonEnabled)
            }
        })

        binding.extDropdown.setOnClickListener {
            val action =
                AuthNumberFragmentDirections.actionAuthNumberFragmentToNumberExtListFragment(this)
            findNavController().navigate(action)
        }

        binding.button.setOnClickListener {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val response = authViewModel.login(LoginBody(binding.editText.text.toString()))
                    Log.d(TAG, "onStart: $response")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
//            val action =
//                AuthNumberFragmentDirections.actionAuthNumberFragmentToOtpFragment(binding.editText.text.toString())
//            findNavController().navigate(action)
        }

        binding.backButton.root.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onSelectExt(countryCode: CountryCode) {
        /**
         *
         * Not working
         * Fragment is recreated on every navigation so all editTexts are again resets.
         * Use viewModels for this
         *
         */
        authViewModel.selectedExt = countryCode.code + " " + countryCode.dialCode
    }

    // Note: Fragments outlive their views. Make sure you clean up any references to the binding class instance
    // in the fragment's onDestroyView() method.
    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel("Fragment Destroyed")
        _binding = null
    }
}