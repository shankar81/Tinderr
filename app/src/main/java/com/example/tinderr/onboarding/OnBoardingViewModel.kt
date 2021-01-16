package com.example.tinderr.onboarding

import android.app.Application
import android.net.Uri
import android.os.FileUtils
import android.util.Log
import androidx.lifecycle.*
import com.example.tinderr.RetrofitService
import com.example.tinderr.datastore.ProtoRepository
import com.example.tinderr.models.Response
import com.example.tinderr.models.User
import com.example.tinderr.onboarding.models.Passion
import com.example.tinderr.onboarding.models.UploadResponse
import com.example.tinderr.onboarding.network.OnBoardingAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.io.File

private const val TAG = "OnBoardingViewModel"

class OnBoardingViewModel(application: Application) : AndroidViewModel(application) {
    var id = 0
    var phone = ""
    var token = ""
    var email = ""
    var name = ""
    var dob: Long = 0
    var gender = ""
    var orientations = ""
    var showOrientations = false
    var showGender = false
    var showMe = ""
    var university = ""
    var passions = ""

    private val dataStoreRepo = ProtoRepository(application)

    val user = dataStoreRepo.readProto

    private var userDetails: User? = null

    init {
        /**
         * Fetch previously selected data from dataStore
         */
        viewModelScope.launch {
            user.collect {
                id = it.id
                phone = it.phone
                token = it.token
                email = it.email
                name = it.name
                dob = it.dob
                gender = it.gender
                orientations = it.orientations
                showOrientations = it.showOrientation
                showGender = it.showGender
                showMe = it.showMe
                university = it.university
                passions = it.passions
            }
        }
    }

    /**
     * Get Passions from server
     */
    fun getPassions() = liveData<Response<List<Passion>>>(Dispatchers.IO) {
        try {
            val response = RetrofitService.retrofit.create(OnBoardingAPI::class.java).getPassions()
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response(null, "Some Error While getting Passions in OnBoardingViewModel", 0))
        }
    }

    /**
     * Save userDetails to server
     */
    fun saveDetails(passions: String) = liveData<Response<User>> {
        userDetails = User(
            userId = id,
            phone = phone,
            token = token,
            email = email,
            name = name,
            dob = dob,
            gender = gender,
            orientations = orientations,
            showOrientations = showOrientations,
            showGender = showGender,
            showMe = showMe,
            university = university,
            passions = passions
        )
        try {
            val response =
                RetrofitService.retrofit.create(OnBoardingAPI::class.java)
                    .saveUserDetails(userDetails!!, "Bearer $token")
            Log.d(TAG, "saveDetails: $response")
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "saveDetails: ", e)
            emit(Response(null, "Error while saving userDetails in OnBoardingViewModel", 0))
        }
    }

    private fun getPart(list: ArrayList<File?>, index: Int): MultipartBody.Part? {
        // Check if index passed is less than list size
        return if (index < list.size) {
            // If file is null return null
            val file = list[index] ?: return null
            val requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), file
            )
            MultipartBody.Part.createFormData("image$index", file.name, requestBody)
        } else {
            null
        }
    }

    /**
     * Upload user images to server
     */
    fun uploadImages(files: ArrayList<File?>) = liveData<Response<UploadResponse>> {
        Log.d(TAG, "uploadImages: $files $token")
        val image1Part: MultipartBody.Part? = getPart(files, 0)
        val image2Part: MultipartBody.Part? = getPart(files, 1)
        val image3Part: MultipartBody.Part? = getPart(files, 2)
        val image4Part: MultipartBody.Part? = getPart(files, 3)
        val image5Part: MultipartBody.Part? = getPart(files, 4)
        val image6Part: MultipartBody.Part? = getPart(files, 5)

        try {
            val response =
                RetrofitService.retrofit.create(OnBoardingAPI::class.java)
                    .uploadImages(
                        "Bearer $token",
                        image1Part,
                        image2Part,
                        image3Part,
                        image4Part,
                        image5Part,
                        image6Part
                    )
            Log.d(TAG, "uploadImages: $response")
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "uploadImages: ", e)
            emit(Response(null, "Error while saving uploadImages in OnBoardingViewModel", 0))
        }
    }

    fun clear() = viewModelScope.launch {
        dataStoreRepo.clear()
    }

    fun updateId(id: Int) = viewModelScope.launch {
        dataStoreRepo.updateId(id)
    }

    fun updateName(name: String) = viewModelScope.launch {
        dataStoreRepo.updateName(name)
    }

    fun updatePhone(phone: String) = viewModelScope.launch {
        dataStoreRepo.updatePhone(phone)
    }

    fun updateToken(token: String) = viewModelScope.launch {
        dataStoreRepo.updateToken(token)
    }

    fun updateEmail(email: String) = viewModelScope.launch {
        dataStoreRepo.updateEmail(email)
    }

    fun updateDob(dob: Long) = viewModelScope.launch {
        dataStoreRepo.updateDob(dob)
    }

    fun updateGender(gender: String) = viewModelScope.launch {
        dataStoreRepo.updateGender(gender)
    }

    fun updateOrientations(orientation: String) = viewModelScope.launch {
        dataStoreRepo.updateOrientations(orientation)
    }

    fun updateShowMe(showMe: String) = viewModelScope.launch {
        dataStoreRepo.updateShowMe(showMe)
    }

    fun updateUniversity(university: String) = viewModelScope.launch {
        dataStoreRepo.updateUniversity(university)
    }

    fun updatePassions(passions: String) = viewModelScope.launch {
        dataStoreRepo.updatePassions(passions)
    }

}
