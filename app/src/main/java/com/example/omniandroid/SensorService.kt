package com.example.omniandroid

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SensorService {

    // 전체 받아오기
    @GET("dev/sensor")
    fun sensors(): Call<List<SensorItem>>

    // 온도 확인
    @GET("dev/sensor/1")
    fun sensorTemp(): Call<List<SensorItem>>

    @GET("dev/sensor/2")
    fun sensorHumi(): Call<List<SensorItem>>

    @GET("dev/sensor/3")
    fun sensorEmf(): Call<List<SensorItem>>


}