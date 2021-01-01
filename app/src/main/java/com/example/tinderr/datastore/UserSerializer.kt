package com.example.tinderr.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.tinderr.UserInfo
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


class UserSerializer : Serializer<UserInfo> {
    override val defaultValue: UserInfo = UserInfo.getDefaultInstance()

    override fun readFrom(input: InputStream): UserInfo {
        return try {
            UserInfo.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override fun writeTo(t: UserInfo, output: OutputStream) {
        t.writeTo(output)
    }
}