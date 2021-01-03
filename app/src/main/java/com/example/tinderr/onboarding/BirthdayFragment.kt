package com.example.tinderr.onboarding

import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.databinding.FragmentBirthdayBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import java.util.*

private const val TAG = "BirthdayFragment"

class BirthdayFragment(private val viewPager: ViewPager2, private val position: Int) : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null

    private val binding
        get() = _binding!!

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

        binding.button.setOnClickListener {
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