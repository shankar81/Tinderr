package com.example.tinderr.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tinderr.R
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.models.CountryCode
import java.io.Serializable

interface Callbacks : Serializable {
    fun onSelectExt(countryCode: CountryCode)
}

class AuthNumberFragment : Fragment(), Callbacks {

    private lateinit var extDropdown: EditText
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var authViewModel: AuthViewModel
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth_number, container, false)

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        extDropdown = view.findViewById(R.id.extDropdown)
        editText = view.findViewById(R.id.editText)
        button = view.findViewById(R.id.button)
        backButton = view.findViewById(R.id.backButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        extDropdown.setText(authViewModel.selectedExt)
        button.updateButton(authViewModel.buttonEnabled)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                authViewModel.buttonEnabled = s?.length == 10
                button.updateButton(authViewModel.buttonEnabled)
            }
        })

        extDropdown.setOnClickListener {
            val action =
                AuthNumberFragmentDirections.actionAuthNumberFragmentToNumberExtListFragment(this)
            findNavController().navigate(action)
        }

        button.setOnClickListener {
            val action =
                AuthNumberFragmentDirections.actionAuthNumberFragmentToOtpFragment(editText.text.toString())
            findNavController().navigate(action)
        }

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onSelectExt(countryCode: CountryCode) {
        /**
         *
         * Not working
         * Fragment is recreated on every navigation so all editTexts are again resets.
         * Use viewModels for this
         *
         */
        authViewModel.selectedExt = countryCode.code + " " + countryCode.dialCode
    }
}