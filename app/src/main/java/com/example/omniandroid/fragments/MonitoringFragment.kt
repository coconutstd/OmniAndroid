package com.example.omniandroid.fragments

import CustomAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omniandroid.R
import com.example.omniandroid.SensorItem
import com.example.omniandroid.SensorService
import kotlinx.android.synthetic.main.fragment_monitoring.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MonitoringFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = CustomAdapter()

        // 이 부분만 좀 다름!
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(getContext())

        val retrofit = Retrofit.Builder()
                .baseUrl("https://4cxysyupk7.execute-api.ap-northeast-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        buttonRequest.setOnClickListener {
            val sensorService = retrofit.create(SensorService::class.java)
            sensorService.sensors().enqueue(object : Callback<List<SensorItem>> {
                override fun onResponse(call: Call<List<SensorItem>>, response: Response<List<SensorItem>>) {
                    adapter.sensorList.addAll(response.body() as List<SensorItem>)
                    adapter.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<List<SensorItem>>, t: Throwable) {

                }
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitoring, container, false)
    }

    companion object {
        fun newInstance(param1: String?, param2: String?): MonitoringFragment {
            return MonitoringFragment()
        }
    }
}