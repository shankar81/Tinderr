package com.example.tinderr.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.tinderr.RetrofitService
import com.example.tinderr.auth.models.LoginBody
import com.example.tinderr.auth.models.VerifyOTPBody
import com.example.tinderr.auth.models.VerifyOTPResponse
import com.example.tinderr.auth.network.AuthAPI
import com.example.tinderr.models.Response
import kotlinx.coroutines.Dispatchers

private const val TAG = "AuthViewModel"

class AuthViewModel : ViewModel() {
    var selectedExt = "IN +91"
    var buttonEnabled = false

    suspend fun login(body: LoginBody) =
        RetrofitService.retrofit.create(AuthAPI::class.java).login(body)

    fun verifyOTP(body: VerifyOTPBody) = liveData<Response<VerifyOTPResponse>>(Dispatchers.IO) {
        try {
            val response = RetrofitService.retrofit.create(AuthAPI::class.java).verifyOTP(body)
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response(null, "Some Error Occurred (VerifyOTP)", 0))
        }
    }

    fun verifyUser(token: String) = liveData<Response<VerifyOTPResponse>>(Dispatchers.IO) {
        try {
            val response = RetrofitService.retrofit.create(AuthAPI::class.java).verify(token)
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "verifyUser: ", e)
            emit(Response(null, "Some Error Occurred (verifyUser)", 0))
        }
    }
}