package com.example.tinderr.onboarding.network

import com.example.tinderr.models.Response
import com.example.tinderr.models.User
import com.example.tinderr.onboarding.models.Passion
import retrofit2.http.*

interface OnBoardingAPI {
    @GET("/passions")
    suspend fun getPassions(): Response<List<Passion>>

    @POST("/userDetails")
    suspend fun saveUserDetails(
        @Body user: User,
        @Header("Authorization") token: String,
    ): Response<User>
}