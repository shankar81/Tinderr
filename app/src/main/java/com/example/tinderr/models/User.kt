package com.example.tinderr.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int = 0,
    val userId: Int = 0,
    val name: String = "",
    val phone: String = "",
    val token: String = "",
    val email: String = "",
    val dob: Long = 0,
    val gender: String = "",
    val showGender: Boolean = false,
    val orientations: String = "",
    val showOrientations: Boolean = false,
    val showMe: String = "",
    val university: String = "",
    val passions: String = "",
)