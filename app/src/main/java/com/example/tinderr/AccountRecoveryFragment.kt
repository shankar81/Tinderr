package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AccountRecoveryFragment : Fragment() {

    private lateinit var button: Button
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_recovery, container, false)

        button = view.findViewById(R.id.button)
        backButton = view.findViewById(R.id.backButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        button.setOnClickListener {
            val action =
                AccountRecoveryFragmentDirections.actionAccountRecoveryFragmentToAuthEmail()
            findNavController().navigate(action)
        }

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}