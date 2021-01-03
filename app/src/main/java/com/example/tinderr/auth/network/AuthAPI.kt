package com.example.tinderr.auth.network

import com.example.tinderr.auth.models.*
import com.example.tinderr.models.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthAPI {
    @POST("/login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>

    @GET("/verifyOtp")
    suspend fun verifyOTP(@Body body:VerifyOTPBody): Response<VerifyOTPResponse>
}