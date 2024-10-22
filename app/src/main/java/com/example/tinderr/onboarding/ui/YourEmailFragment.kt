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
import com.example.tinderr.databinding.FragmentYourEmailBinding
import com.example.tinderr.onboarding.OnBoardingViewModel

class YourEmailFragment(
    private val viewPager: ViewPager2,
    private val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment() {

    private var _binding: FragmentYourEmailBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYourEmailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.viewPagerCallback(viewPager, position, binding.editText)
        binding.editText.setText(viewModel.email)

        binding.button.updateButton(Utils.isEmailValid(binding.editText.text.toString()))

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.button.updateButton(Utils.isEmailValid(s?.toString()))
            }
        })

        binding.button.setOnClickListener {
            viewModel.updateEmail(binding.editText.text.toString())
            viewPager.setCurrentItem(position + 1, true)
        }

        binding.appCompatButton.setOnClickListener {
            binding.editText.requestFocus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}