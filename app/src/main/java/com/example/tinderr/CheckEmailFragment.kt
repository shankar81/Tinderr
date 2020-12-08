package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class CheckEmailFragment : Fragment() {

    private lateinit var changeEmail: TextView
    private lateinit var useNumber: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check_email, container, false)

        changeEmail = view.findViewById(R.id.changeEmail)
        useNumber = view.findViewById(R.id.useNumber)

        return view
    }

    override fun onStart() {
        super.onStart()

        changeEmail.setOnClickListener {
            val action = CheckEmailFragmentDirections.actionCheckEmailFragmentToAuthEmail()
            findNavController().navigate(action)
        }

        useNumber.setOnClickListener {
            val action = CheckEmailFragmentDirections.actionCheckEmailFragmentToAuthNumberFragment()
            findNavController().navigate(action)
        }
    }

}