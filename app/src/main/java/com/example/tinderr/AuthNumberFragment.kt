package com.example.tinderr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AuthNumberFragment : Fragment() {

    private lateinit var numberDropdown: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_number, container, false)

        numberDropdown = view.findViewById(R.id.numberDropdown)

        return view
    }

    override fun onStart() {
        super.onStart()

        numberDropdown.setOnClickListener {
            val action =
                AuthNumberFragmentDirections.actionAuthNumberFragmentToNumberExtListFragment()
            findNavController().navigate(action)
        }
    }
}