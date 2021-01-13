package com.example.tinderr.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import com.example.tinderr.UserInfo
import com.example.tinderr.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class ProtoRepository(context: Context) {

    private val dataStore: DataStore<UserInfo> =
        context.createDataStore("my_data", serializer = UserSerializer())

    val readProto: Flow<UserInfo> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(UserInfo.getDefaultInstance())
            } else {
                throw e
            }
        }

    suspend fun clear() {
        dataStore.updateData { it.toBuilder().clear().build() }
    }

    suspend fun updateName(name: String) {
        dataStore.updateData {
            it.toBuilder()
                .setName(name)
                .build()
        }
    }

    suspend fun updatePhone(phone: String) {
        dataStore.updateData {
            it.toBuilder()
                .setPhone(phone)
                .build()
        }
    }

    suspend fun updateToken(token: String) {
        dataStore.updateData {
            it.toBuilder()
                .setToken(token)
                .build()
        }
    }

    suspend fun updateEmail(email: String) {
        dataStore.updateData {
            it.toBuilder()
                .setEmail(email)
                .build()
        }
    }

    suspend fun updateDob(dob: Long) {
        dataStore.updateData {
            it.toBuilder()
                .setDob(dob)
                .build()
        }
    }

    suspend fun updateGender(gender: String) {
        dataStore.updateData {
            it.toBuilder()
                .setGender(gender)
                .build()
        }
    }

    suspend fun updateOrientations(orientation: String) {
        dataStore.updateData {
            it.toBuilder()
                .setOrientations(orientation)
                .build()
        }
    }

    suspend fun updateShowMe(showMe: String) {
        dataStore.updateData {
            it.toBuilder()
                .setShowMe(showMe)
                .build()
        }
    }

    suspend fun updateUniversity(university: String) {
        dataStore.updateData {
            it.toBuilder()
                .setUniversity(university)
                .build()
        }
    }

    suspend fun updatePassions(passions: String) {
        dataStore.updateData {
            it.toBuilder()
                .setPassions(passions)
                .build()
        }
    }

    suspend fun updateId(id: Int) {
        dataStore.updateData {
            it.toBuilder()
                .setId(id)
                .build()
        }
    }

    suspend fun updateValue(user: User) {
        dataStore.updateData {
            it.toBuilder()
                .setId(user.userId)
                .setName(user.name)
                .setPhone(user.phone)
                .setToken(user.token)
                .setEmail(user.email)
                .setDob(user.dob)
                .setGender(user.gender)
                .setShowGender(user.showGender)
                .setOrientations(user.name)
                .setShowOrientation(user.showOrientations)
                .setShowMe(user.showMe)
                .setUniversity(user.university)
                .setPassions(user.passions)
                .build()
        }
    }

}