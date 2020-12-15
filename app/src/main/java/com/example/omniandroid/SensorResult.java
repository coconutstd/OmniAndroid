package com.example.omniandroid;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class SensorResult {
    @SerializedName("id")
    private int id;

    @SerializedName("payload")
    private ArrayList<Payload> payload;

    @SerializedName("positionId")
    private String positionId;

    @Override
    public String toString() {
        return "SensorResult{" +
                ", id=" + id +
                ", payload=" + payload +
                ", positionId=" + positionId + "}";
    }
}
