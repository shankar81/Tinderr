package com.example.tinderr.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tinderr.R

class AuthFragment : Fragment() {

    private lateinit var recoveryButton: TextView
    private lateinit var policyText: TextView
    private lateinit var numberButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)

        recoveryButton = view.findViewById(R.id.recoveryButton)
        policyText = view.findViewById(R.id.policyText)
        numberButton = view.findViewById(R.id.numberButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        recoveryButton.setOnClickListener {
            val action = AuthFragmentDirections.actionAuthFragmentToAccountRecoveryFragment()
            findNavController().navigate(action)
        }

        numberButton.setOnClickListener {
            val action = AuthFragmentDirections.actionAuthFragmentToAuthNumberFragment()
            findNavController().navigate(action)
        }
    }
}



