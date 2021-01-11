package com.example.tinderr.auth.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.tinderr.R
import com.example.tinderr.onboarding.OnBoardingViewModel

private const val TAG = "SplashFragment"

class SplashFragment : Fragment() {

    private lateinit var onBoardingViewModel: OnBoardingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        onBoardingViewModel = ViewModelProviders.of(this).get(OnBoardingViewModel::class.java)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            onBoardingViewModel.user.asLiveData().observe(viewLifecycleOwner, {
                Log.d(TAG, "onViewCreated: $it")
                if (it == null || it.token.isNullOrEmpty()) {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthFragment())
                } else {
                    if (it.passions.isNullOrEmpty()) {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment())
                    } else {
                        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToNavigation2())
                    }
                }
            })
        }, 3000)
    }
}