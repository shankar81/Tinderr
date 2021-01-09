package com.example.tinderr.onboarding.ui

import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.Utils
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.Utils.viewPagerCallback
import com.example.tinderr.databinding.FragmentBirthdayBinding
import com.example.tinderr.onboarding.OnBoardingViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import java.util.*

class BirthdayFragment(
    private val viewPager: ViewPager2,
    private val position: Int,
    val viewModel: OnBoardingViewModel
) : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null

    private val binding
        get() = _binding!!

    private var selectedDOBTime = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBirthdayBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerCallback(viewPager, position, binding.editText)

        selectedDOBTime = Calendar.getInstance().apply {
            timeInMillis = viewModel.dob

            val d = get(Calendar.DAY_OF_MONTH).toString()
            val m = if (get(Calendar.MONTH) < 10) "0" + get(Calendar.MONTH) else get(Calendar.MONTH)
            val y = if (get(Calendar.YEAR) < 10) "0" + get(Calendar.YEAR) else get(Calendar.YEAR)
            binding.editText.setText(
                d + "" + m + "" + y
            )
        }.timeInMillis

        binding.button.updateButton(viewModel.dob != 0L)

        binding.button.setOnClickListener {
            Utils.closeKeyboard(requireActivity())
            viewModel.updateDob(selectedDOBTime)
            viewPager.setCurrentItem(position + 1, true)
        }

        binding.editText.inputType = InputType.TYPE_DATETIME_VARIATION_DATE
        binding.editText.keyListener = DigitsKeyListener.getInstance("1234567890/")

        installOn(
            binding.editText,
            "[00]/[00]/[0000]",
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    if (maskFilled) {
                        val cal = Calendar.getInstance()
                        val day = extractedValue.substring(0, 2).toInt()
                        val month = extractedValue.substring(2, 4).toInt()
                        val year = extractedValue.substring(4, extractedValue.length).toInt()

                        if (month in 1..12 && year <= cal.get(Calendar.YEAR)) {
                            val maximumDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                            cal.apply {
                                set(Calendar.MONTH, month)
                                set(Calendar.YEAR, year)
                                set(Calendar.DAY_OF_MONTH, day)
                            }
                            selectedDOBTime = cal.timeInMillis
                            binding.button.updateButton(day <= maximumDay)
                        } else {
                            binding.button.updateButton(false)
                        }
                    } else {
                        binding.button.updateButton(false)
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}