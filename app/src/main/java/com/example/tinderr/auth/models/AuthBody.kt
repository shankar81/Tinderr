package com.example.tinderr.auth.models

data class LoginBody(val phone: String)

data class VerifyOTPBody(val phone: String, val otp: String)