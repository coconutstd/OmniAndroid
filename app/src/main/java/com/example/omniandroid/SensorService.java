package com.example.omniandroid;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorService {
    @GET("dev/{sensor}")
    Call<SensorResult> getSensors(@Path("sensor") String sensor);
}
