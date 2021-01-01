package com.example.tinderr.models

data class Response<T>(val data: T?, val msg: String, val result: Int)