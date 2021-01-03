package com.example.tinderr.onboarding

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentSelectGenderBinding

class SelectGenderFragment(private val viewPager: ViewPager2, private val position: Int) :
    Fragment() {

    private var _binding: FragmentSelectGenderBinding? = null

    private val binding
        get() = _binding!!

    private var selectedGender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectGenderBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button.setOnClickListener {
            viewPager.setCurrentItem(position + 1, true)
        }

        binding.woman.setOnClickListener {
            updatedSelected(binding.woman)

            updatedNonSelected(binding.man)
            updatedNonSelected(binding.other)
        }

        binding.man.setOnClickListener {
            updatedSelected(binding.man)

            updatedNonSelected(binding.woman)
            updatedNonSelected(binding.other)
        }

        binding.other.setOnClickListener {
            updatedSelected(binding.other)

            updatedNonSelected(binding.man)
            updatedNonSelected(binding.woman)
        }
    }

    private fun updatedSelected(textView: TextView) {
        if (selectedGender == null) {
            binding.button.updateButton(true)
        }
        selectedGender = textView.text.toString()

        textView.setTextColor(Utils.getColor(resources, R.color.pink_700))
        textView.background =
            Utils.getDrawable(resources, R.drawable.radio_textview_primary)
    }

    private fun updatedNonSelected(textView: TextView) {
        textView.setTextColor(Color.parseColor("#aaaaaa"))
        textView.background =
            Utils.getDrawable(resources, R.drawable.radio_textview)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}