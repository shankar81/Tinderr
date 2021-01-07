package com.example.tinderr.onboarding

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentFirstNameBinding
import com.example.tinderr.databinding.FragmentOnBoardingBinding

class FirstNameFragment(private val viewPager: ViewPager2, private val position: Int) : Fragment() {

    private var _binding: FragmentFirstNameBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstNameBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.viewPagerCallback(viewPager, position, binding.editText)
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.button.updateButton(s?.toString()?.length ?: 0 > 2)
            }
        })

        binding.button.setOnClickListener {
            viewPager.setCurrentItem(position + 1, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}