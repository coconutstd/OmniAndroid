package com.example.omniandroid.fragments

import CustomAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.omniandroid.R
import com.example.omniandroid.SensorItem
import com.example.omniandroid.SensorService
import kotlinx.android.synthetic.main.fragment_monitoring.*
import kotlinx.android.synthetic.main.fragment_monitoring.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MonitoringFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val adapter = CustomAdapter();
        val view = inflater.inflate(R.layout.fragment_monitoring, container, false) as ViewGroup
        val recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        val buttonRequest = view.findViewById<View>(R.id.buttonRequest) as Button

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitoring, container, false)
    }

    companion object {
        fun newInstance(param1: String?, param2: String?): MonitoringFragment {
            return MonitoringFragment()
        }
    }


}