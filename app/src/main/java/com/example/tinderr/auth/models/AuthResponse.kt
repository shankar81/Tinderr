package com.example.tinderr.auth.models

import com.example.tinderr.models.User

data class LoginResponse(val user: User, val otp: String)

data class VerifyOTPResponse(val user: User, val token: String)