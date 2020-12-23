package com.example.omniandroid

import retrofit2.Call
import retrofit2.http.GET

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
    @GET("dev/value/1")
    fun valueHumi(): Call<List<ValueItem>>

    @GET("dev/value/2")
    fun valueTemp(): Call<List<ValueItem>>

    @GET("dev/value/3")
    fun valueEmf(): Call<List<ValueItem>>

    @GET("dev/recent-score/5c0c966f-9461-458f-a127-91a8b5539c38")
    fun valueScore(): Call<List<ScoreItem>>
}