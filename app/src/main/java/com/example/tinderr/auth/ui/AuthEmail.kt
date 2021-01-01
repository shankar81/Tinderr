package com.example.tinderr.auth.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tinderr.R
import com.example.tinderr.Utils
import com.example.tinderr.Utils.updateButton

class AuthEmail : Fragment() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_email, container, false)

        editText = view.findViewById(R.id.editText)
        button = view.findViewById(R.id.button)
        backButton = view.findViewById(R.id.backButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                button.updateButton(Utils.isEmailValid(s?.toString()))
            }
        })

        button.setOnClickListener {
            val action =
                AuthEmailDirections.actionAuthEmailToCheckEmailFragment()
            findNavController().navigate(action)
        }

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}