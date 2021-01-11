package com.example.tinderr.auth.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinderr.LoaderFragment
import com.example.tinderr.R
import com.example.tinderr.Utils.closeKeyboard
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.auth.AuthViewModel
import com.example.tinderr.auth.models.VerifyOTPBody
import com.example.tinderr.onboarding.OnBoardingViewModel

private const val TAG = "OtpFragment"

class OtpFragment : Fragment() {

    private lateinit var phoneNumber: TextView
    private lateinit var button: Button
    private lateinit var backButton: ImageView
    private val otps = arrayListOf<EditText>()
    private val args: OtpFragmentArgs by navArgs()
    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText

    private lateinit var authViewModel: AuthViewModel
    private lateinit var onBoardingViewModel: OnBoardingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        onBoardingViewModel = ViewModelProviders.of(this).get(OnBoardingViewModel::class.java)

        phoneNumber = view.findViewById(R.id.phoneNumber)
        backButton = view.findViewById(R.id.backButton)
        button = view.findViewById(R.id.button)
        otp1 = view.findViewById(R.id.otp1)
        otp2 = view.findViewById(R.id.otp2)
        otp3 = view.findViewById(R.id.otp3)
        otp4 = view.findViewById(R.id.otp4)
        otp5 = view.findViewById(R.id.otp5)
        otp6 = view.findViewById(R.id.otp6)

        otps.add(otp1)
        otps.add(otp2)
        otps.add(otp3)
        otps.add(otp4)
        otps.add(otp5)
        otps.add(otp6)

        return view
    }

    override fun onStart() {
        super.onStart()

        phoneNumber.text = args.number

        /**
         * Attaching touch listener for every otp EditText
         */
        otps.mapIndexed { index, _ ->
            if (index < otps.size) {
                onOtpEntered(index)
            }
        }

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        button.setOnClickListener {
            closeKeyboard(requireActivity())
            LoaderFragment.show()
            val otp = otps.joinToString("") { it.text.toString() }
            authViewModel.verifyOTP(VerifyOTPBody(args.number, otp)).observe(
                viewLifecycleOwner,
                { response ->
                    Log.d(TAG, "onStart: $response")
                    if (response.result == 1 && response.data != null) {
                        onBoardingViewModel.updatePhone(response.data.user.phone)
                        onBoardingViewModel.updateToken(response.data.token)
                        onBoardingViewModel.updateId(response.data.user.id)

                        onBoardingViewModel.user.asLiveData().observe(viewLifecycleOwner, {
                            val action = when {
                                response.data.oldUser -> {
                                    OtpFragmentDirections.actionOtpFragmentToNavigation2()
                                }
                                it.passions.isNullOrEmpty() -> {
                                    OtpFragmentDirections.actionOtpFragmentToOnBoardingFragment()
                                }
                                else -> {
                                    OtpFragmentDirections.actionOtpFragmentToNavigation2()
                                }
                            }
                            findNavController().navigate(action)
                        })
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Wrong OTP",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    LoaderFragment.hide()

                })
        }
    }

    private fun onOtpEntered(index: Int) {
        val prev = if (index > 0) otps[index - 1] else null
        val curr = otps[index]
        val next = if (index < (otps.size - 1)) otps[index + 1] else null
        curr.setOnKeyListener { _, _, event ->
            /**
             *
             * This could run two times without first condition
             * 1. ACTION_DOWN
             * 2. ACTION_UP
             *
             */
            if (event.action == KeyEvent.ACTION_UP && event.unicodeChar != 0 && curr.text.toString()
                    .isNotBlank()
            ) {
                curr.setText(event.displayLabel.toString())
                next?.requestFocus()
            }

            /** When press delete from keypad
             *
             * Checks for ACTION_DOWN
             * because checking if empty before ACTION_UP (before EditText update)
             *
             */
            if (event.unicodeChar == 0 && event.action == KeyEvent.ACTION_DOWN
            ) {
                if (curr.text.toString().isNotBlank()) {
                    curr.setText("")
                } else {
                    prev?.requestFocus()
                }
            }
            curr.setSelection(curr.text.length)
            updateButton()
            false
        }
    }

    private fun updateButton() {
        val blanks = otps.filter {
            it.text.toString().isBlank()
        }
        button.updateButton(blanks.isEmpty())
    }
}