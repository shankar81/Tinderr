package com.example.tinderr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tinderr.datastore.ProtoRepository
import com.example.tinderr.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStoreRepo = ProtoRepository(application)

    val user = dataStoreRepo.readProto.asLiveData()

    fun updateValue(user: User) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepo.updateValue(user)
    }
}