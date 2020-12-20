package com.example.omniandroid

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SensorService {

    // 전체 받아오기
    @GET("dev/sensor")
    fun sensors(): Call<List<SensorItem>>

    // 습도
    @GET("dev/sensor/1")
    fun sensorHumi(): Call<List<SensorItem>>
    // 온도
    @GET("dev/sensor/2")
    fun sensorTemp(): Call<List<SensorItem>>
    //이산화탄소
    @GET("dev/sensor/3")
    fun sensorEmf(): Call<List<SensorItem>>


}