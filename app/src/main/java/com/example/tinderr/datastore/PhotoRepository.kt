package com.example.tinderr.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import com.example.tinderr.UserInfo
import com.example.tinderr.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class PhotoRepository(context: Context) {

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

    suspend fun updateValue(user: User) {
        dataStore.updateData {
            it.toBuilder()
                .setId(user.id)
                .setName(user.name)
                .setPhone(user.phone)
                .setToken(user.token)
                .build()
        }
    }
}