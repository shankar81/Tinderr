package com.example.tinderr.onboarding.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.Utils
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentMyUniversityBinding
import com.example.tinderr.onboarding.OnBoardingViewModel

class MyUniversityFragment(
    val viewPager: ViewPager2,
    val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment() {

    private var _binding: FragmentMyUniversityBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyUniversityBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.viewPagerCallback(viewPager, position, binding.editText)
        binding.editText.setText(viewModel.university)

        binding.button.updateButton(binding.editText.text.toString().length > 2)

        binding.editText.setText(viewModel.university)
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.button.updateButton(s?.toString()?.length ?: 0 > 2)
            }
        })

        binding.button.setOnClickListener {
            Utils.closeKeyboard(requireActivity())
            viewModel.updateUniversity(binding.editText.text.toString())
            viewPager.setCurrentItem(position + 1, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}