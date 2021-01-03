package com.example.tinderr.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tinderr.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

    private val fragments = arrayListOf<Fragment>()

    private var _binding: FragmentOnBoardingBinding? = null

    private val binding
        get() = _binding!!

    private val backButtonCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater)

        // Disable user scroll
        binding.viewPager.isUserInputEnabled = false

        binding.viewPager.adapter = ViewPagerAdapter(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(backButtonCallback)

        fragments.addAll(
            listOf(
                YourEmailFragment(binding.viewPager, 0),
                FirstNameFragment(binding.viewPager, 1),
                BirthdayFragment(binding.viewPager, 2),
                SelectGenderFragment(binding.viewPager, 3),
                SexualOrientationFragment(binding.viewPager, 4),
                ShowMeFragment(binding.viewPager, 5),
                MyUniversityFragment(binding.viewPager, 6),
                PassionsFragment(binding.viewPager, 7),
            )
        )

        binding.viewPager.offscreenPageLimit = fragments.size
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