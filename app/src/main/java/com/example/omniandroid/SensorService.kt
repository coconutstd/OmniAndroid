package com.example.omniandroid

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SensorService {
    // 온도 확인
    @GET("dev/sensor/:user")
    fun sensors(): Call<List<SensorItem>>

}