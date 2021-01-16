package com.example.tinderr.onboarding.network

import com.example.tinderr.models.Response
import com.example.tinderr.models.User
import com.example.tinderr.onboarding.models.Passion
import com.example.tinderr.onboarding.models.UploadResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface OnBoardingAPI {
    @GET("/passions")
    suspend fun getPassions(): Response<List<Passion>>

    @POST("/userDetails")
    suspend fun saveUserDetails(
        @Body user: User,
        @Header("Authorization") token: String,
    ): Response<User>

    @Multipart
    @POST("/files")
    suspend fun uploadImages(
        @Header("Authorization") token: String,
        @Part image1: MultipartBody.Part? = null,
        @Part image2: MultipartBody.Part? = null,
        @Part image3: MultipartBody.Part? = null,
        @Part image4: MultipartBody.Part? = null,
        @Part image5: MultipartBody.Part? = null,
        @Part image6: MultipartBody.Part? = null,
    ): Response<UploadResponse>
}