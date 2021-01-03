package com.example.tinderr.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.R
import com.example.tinderr.databinding.FragmentSelectGenderBinding
import com.example.tinderr.databinding.FragmentShowMeBinding

class ShowMeFragment(val viewPager: ViewPager2, val position: Int) : Fragment() {

    private var _binding: FragmentShowMeBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowMeBinding.inflate(inflater)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}