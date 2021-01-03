package com.example.tinderr.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tinderr.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

    private val fragments = arrayListOf(
        YourEmailFragment(),
        FirstNameFragment(),
        BirthdayFragment(),
        SelectGenderFragment(),
        SexualOrientationFragment(),
        ShowMeFragment(),
        MyUniversityFragment(),
        PassionsFragment(),
    )

    private var _binding: FragmentOnBoardingBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater)

        // Disable user scroll
//        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.adapter = ViewPagerAdapter(this)

        return binding.root
    }


    private inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}