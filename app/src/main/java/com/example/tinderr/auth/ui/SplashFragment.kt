package com.example.tinderr.auth.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.tinderr.R
import com.example.tinderr.auth.AuthViewModel
import com.example.tinderr.onboarding.OnBoardingViewModel

class SplashFragment : Fragment() {

    private lateinit var onBoardingViewModel: OnBoardingViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        onBoardingViewModel = ViewModelProviders.of(this).get(OnBoardingViewModel::class.java)
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Verify User from server by sending token
         */
        onBoardingViewModel.user.asLiveData().observe(viewLifecycleOwner, {
            if (it == null || it.token.isNullOrEmpty()) {
                /**
                 * If userDetails OR token is null or empty
                 */
                onBoardingViewModel.clear()
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToAuthFragment()
                )
            } else {
                authViewModel.verifyUser("Bearer ${it.token}")
                    .observe(viewLifecycleOwner, { response ->
                        if (response.result == 1 && response.data != null) {
                            if (response.data.oldUser) {
                                /**
                                 * If user has filled all onBoarding details
                                 */
                                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToNavigation2())
                            } else {
                                /**
                                 * If user has not filled all onBoarding details
                                 */
                                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment())
                            }
                        }
                    })
            }
        })
    }
}