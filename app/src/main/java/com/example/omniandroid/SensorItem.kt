package com.example.omniandroid

data class SensorItem(
    val id: String,
    val createdAt: String,
    val sort: String,
    val userId: String,
    val humi: String,
    val temp: String,
    val emf: String
)