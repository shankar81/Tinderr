package com.example.tinderr.onboarding

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.tinderr.RetrofitService
import com.example.tinderr.datastore.ProtoRepository
import com.example.tinderr.models.Response
import com.example.tinderr.models.User
import com.example.tinderr.onboarding.models.Passion
import com.example.tinderr.onboarding.network.OnBoardingAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "OnBoardingViewModel"

class OnBoardingViewModel(application: Application) : AndroidViewModel(application) {
    var id = 0
    var phone = ""
    var token = ""
    var email = ""
    var name = ""
    var dob: Long = 0
    var gender = ""
    var orientation = ""
    var showMe = ""
    var university = ""
    var passions = ""

    private val dataStoreRepo = ProtoRepository(application)

    private val user = dataStoreRepo.readProto

    init {
        viewModelScope.launch {
            user.collect {
                id = it.id
                phone = it.phone
                token = it.token
                email = it.email
                name = it.name
                dob = it.dob
                gender = it.gender
                orientation = it.orientations
                showMe = it.showMe
                university = it.university
                passions = it.passions
            }
        }
    }

    fun getPassions() = liveData<Response<List<Passion>>>(Dispatchers.IO) {
        try {
            val response = RetrofitService.retrofit.create(OnBoardingAPI::class.java).getPassions()
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response(null, "Some Error While getting Passions in OnBoardingViewModel", 0))
        }
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
