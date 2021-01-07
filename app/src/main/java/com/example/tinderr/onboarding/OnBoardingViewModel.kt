package com.example.tinderr.onboarding

import androidx.lifecycle.ViewModel

class OnBoardingViewModel : ViewModel() {
    var email = ""
    var name = ""
    var dob: Long = 0
    var gender = ""
    var orientation = ""
    var showMe = ""
    var university = ""
    var passions = arrayListOf<String>()
}