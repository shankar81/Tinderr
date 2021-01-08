package com.example.tinderr.onboarding.network

import com.example.tinderr.models.Response
import com.example.tinderr.onboarding.models.Passion
import retrofit2.http.GET

interface OnBoardingAPI {
    @GET("/passions")
    suspend fun getPassions(): Response<List<Passion>>
}