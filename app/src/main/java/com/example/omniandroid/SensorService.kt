package com.example.omniandroid

import retrofit2.Call
import retrofit2.http.GET

interface SensorService {
    @GET("dev/sensor/user_temp")
    fun sensors(): Call<List<SensorItem>>
}