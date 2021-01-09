package com.example.tinderr.onboarding.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentShowMeBinding
import com.example.tinderr.onboarding.OnBoardingViewModel

private const val TAG = "ShowMeFragment"

class ShowMeFragment(
    val viewPager: ViewPager2,
    val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment() {

    private var _binding: FragmentShowMeBinding? = null

    private val binding
        get() = _binding!!

    private var selectedGender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowMeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.viewPagerCallback(viewPager, position, null, requireActivity())

        selectedGender = viewModel.showMe
        binding.button.updateButton(selectedGender != null && selectedGender!!.isNotEmpty())

        updateFromPrevAction()

        binding.button.setOnClickListener {
            viewModel.updateShowMe(selectedGender ?: "")
            viewPager.setCurrentItem(position + 1, true)
        }

        binding.woman.setOnClickListener {
            updatedSelected(binding.woman)

            updatedNonSelected(binding.man)
            updatedNonSelected(binding.everyone)
        }

        binding.man.setOnClickListener {
            updatedSelected(binding.man)

            updatedNonSelected(binding.woman)
            updatedNonSelected(binding.everyone)
        }

        binding.everyone.setOnClickListener {
            updatedSelected(binding.everyone)

            updatedNonSelected(binding.man)
            updatedNonSelected(binding.woman)
        }
    }

    private fun updatedSelected(textView: TextView) {
        if (selectedGender.isNullOrEmpty()) {
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

    /**
     * Check previous selected gender if available
     */
    private fun updateFromPrevAction() {
        Log.d(TAG, "updateFromPrevAction: $selectedGender")
        if (!selectedGender.isNullOrEmpty()) {
            when (selectedGender) {
                "Woman" -> {
                    updatedSelected(binding.woman)

                    updatedNonSelected(binding.man)
                    updatedNonSelected(binding.everyone)
                }
                "Man" -> {
                    updatedSelected(binding.man)

                    updatedNonSelected(binding.woman)
                    updatedNonSelected(binding.everyone)
                }
                "Everyone" -> {
                    updatedSelected(binding.everyone)

                    updatedNonSelected(binding.man)
                    updatedNonSelected(binding.woman)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}