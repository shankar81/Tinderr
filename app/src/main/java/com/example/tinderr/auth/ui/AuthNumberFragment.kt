package com.example.tinderr.auth.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tinderr.CHANNEL_ID
import com.example.tinderr.LoaderFragment
import com.example.tinderr.R
import com.example.tinderr.Utils.showKeyboard
import com.example.tinderr.Utils.updateButton
import com.example.tinderr.auth.AuthViewModel
import com.example.tinderr.auth.models.LoginBody
import com.example.tinderr.databinding.FragmentAuthNumberBinding
import com.example.tinderr.models.CountryCode
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.example.tinderr.auth.AppSignatureHelper
import kotlinx.coroutines.*
import java.io.Serializable

interface Callbacks : Serializable {
    fun onSelectExt(countryCode: CountryCode)
}

class AuthNumberFragment : Fragment(), Callbacks {

    private lateinit var notificationManager: NotificationManager

    private var _binding: FragmentAuthNumberBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    private val coroutineScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Send this code to server
        val signatureHelper = AppSignatureHelper(context)

        _binding = FragmentAuthNumberBinding.inflate(inflater)

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        startSMSRetriever()

        showKeyboard(binding.editText)

        binding.extDropdown.setText(authViewModel.selectedExt)
        binding.button.updateButton(authViewModel.buttonEnabled)

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                authViewModel.buttonEnabled = s?.length == 10
                binding.button.updateButton(authViewModel.buttonEnabled)
            }
        })

        binding.extDropdown.setOnClickListener {
            val action =
                AuthNumberFragmentDirections.actionAuthNumberFragmentToNumberExtListFragment(this)
            findNavController().navigate(action)
        }

        binding.button.setOnClickListener {
            LoaderFragment.show()
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val response = authViewModel.login(LoginBody(binding.editText.text.toString()))
                    if (response.result == 1 && response.data != null) {
                        showNotification(response.data.otp)
                        val action =
                            AuthNumberFragmentDirections.actionAuthNumberFragmentToOtpFragment(
                                binding.editText.text.toString(), response.data.otp
                            )
                        withContext(Dispatchers.Main) {
                            findNavController().navigate(action)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                withContext(Dispatchers.Main) {
                    LoaderFragment.hide()
                }
            }
        }

        binding.backButton.root.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showNotification(otp: String) {
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Tinderr OTP")
            .setContentText("Your one time OTP is: $otp")
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
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

    // Add Listener for OTP SMS
    private fun startSMSRetriever() {
        val client = SmsRetriever.getClient(requireContext())
        client.startSmsRetriever()
    }

    // Note: Fragments outlive their views. Make sure you clean up any references to the binding class instance
    // in the fragment's onDestroyView() method.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}