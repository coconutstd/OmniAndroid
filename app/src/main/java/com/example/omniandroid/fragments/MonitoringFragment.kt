package com.example.omniandroid.fragments

import CustomAdapter
import android.os.Bundle
import android.util.Log
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
        val buttonRequestTemp = view.findViewById<View>(R.id.buttonRequestTemp) as Button
//        val buttonRequestHumi = view.findViewById<View>(R.id.buttonRequestHumi) as Button
//        val buttonRequestEmf = view.findViewById<View>(R.id.buttonRequestHumi) as Button

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://4cxysyupk7.execute-api.ap-northeast-2.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        buttonRequestTemp.setOnClickListener {
            val sensorService = retrofit.create(SensorService::class.java)
            sensorService.sensors().enqueue(object : Callback<List<SensorItem>> {
                override fun onResponse(call: Call<List<SensorItem>>, response: Response<List<SensorItem>>) {
                    adapter.sensorList.addAll(response.body() as List<SensorItem>)
                    Log.d("sensorList", "temp들어왔니?!!??")
                    adapter.notifyDataSetChanged()
                    Log.d("payloadCheck", adapter.sensorList[1].payload.toString())
                    // 만약에 이거 제대로 찍히면 sensorList.length만큼 루프 돌아서 수치 비교 후,
                    // 여기서 푸쉬알림 추가 or 카카오톡 알림 메시지 전송
                }
                override fun onFailure(call: Call<List<SensorItem>>, t: Throwable) {
                    Log.d("sensorList", "temp안들어왔니?!!??")
                }
            })
        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        fun newInstance(param1: String?, param2: String?): MonitoringFragment {
            return MonitoringFragment()
        }
    }

}