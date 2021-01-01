package com.example.tinderr.auth

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tinderr.R
import com.example.tinderr.Utils.updateButton

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)

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
         *
         * Attaching touch listener for every otp EditText
         *
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
            val action = OtpFragmentDirections.actionOtpFragmentToAccessLocationFragment()
            findNavController().navigate(action)
        }
    }

    /**
     *
     * Change this method
     * Use array of EditText to handle backButton and keyPress effectively based on index
     *
     */
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