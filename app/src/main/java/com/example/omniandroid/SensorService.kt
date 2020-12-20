package com.example.omniandroid

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SensorService {

    // 전체, 온도, 습도, 이산화탄소 리스트
    @GET("dev/sensor")
    fun sensors(): Call<List<SensorItem>>

    @GET("dev/sensor/1")
    fun sensorHumi(): Call<List<SensorItem>>

    @GET("dev/sensor/2")
    fun sensorTemp(): Call<List<SensorItem>>

    @GET("dev/sensor/3")
    fun sensorEmf(): Call<List<SensorItem>>


    // 온도, 습도, 이산화탄소 값
    @GET("value/1")
    fun valueHumi(): Call<String>

    @GET("value/2")
    fun valueTemp(): Call<String>

    @GET("value/3")
    fun valueEmp(): Call<String>
}