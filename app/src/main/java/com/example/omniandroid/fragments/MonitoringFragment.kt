package com.example.omniandroid.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.omniandroid.*
import kotlinx.android.synthetic.main.fragment_monitoring.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class MonitoringFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val adapterTemp = ValueCustomAdapter();
        val adapterHumi = ValueCustomAdapter();
        val adapterEmf = ValueCustomAdapter();
        val adapterScore = ScoreCustomAdapter();

        val view = inflater.inflate(R.layout.fragment_monitoring, container, false) as ViewGroup

        var valueTemp = view.findViewById(R.id.valueTemp) as TextView
        var valueHumi = view.findViewById(R.id.valueHumi) as TextView
        var valueEmf = view.findViewById(R.id.valueEmf) as TextView

        val recyclerViewHumi = view.findViewById<View>(R.id.recyclerViewHumi) as RecyclerView
        val recyclerViewEmf = view.findViewById<View>(R.id.recyclerViewEmf) as RecyclerView
        val recyclerValueTemp = view.findViewById<View>(R.id.recyclerViewTemp) as RecyclerView
        val recyclerScore = view.findViewById<View>(R.id.recyclerViewScore) as RecyclerView

        recyclerValueTemp.layoutManager = LinearLayoutManager(context)
        recyclerValueTemp.adapter = adapterTemp
        recyclerViewHumi.layoutManager = LinearLayoutManager(context)
        recyclerViewHumi.adapter = adapterHumi
        recyclerViewEmf.layoutManager = LinearLayoutManager(context)
        recyclerViewEmf.adapter = adapterEmf
        recyclerScore.layoutManager = LinearLayoutManager(context)
        recyclerScore.adapter = adapterScore

        val retrofit = Retrofit.Builder()
            .baseUrl("https://4cxysyupk7.execute-api.ap-northeast-2.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var scoreTemp = 0
        var scoreHumi = 0
        var scoreEmf = 0

        var temp = 0
        var count = 0
        val btn_onoff = view.findViewById<Button>(R.id.onoff)

        val scoreService = retrofit.create(SensorService::class.java)
        scoreService.valueScore().enqueue(object : Callback<List<ScoreItem>> {
            override fun onResponse(call: Call<List<ScoreItem>>, response: Response<List<ScoreItem>>) {
                Log.d("들어오긴 했니????", "어??")
                Log.d("score.length 나오니?", adapterScore.scoreList.size.toString())
                adapterScore.scoreList.addAll(response.body() as List<ScoreItem>)
                adapterScore.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<ScoreItem>>, t: Throwable) {
                Log.d("scoreList", " 안들어왔니?!!??")
            }
        })

        btn_onoff.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.switch_off))
        btn_onoff.setOnClickListener() {
//            while(count == 10) {

                if (temp == 0) {
                    val valueService = retrofit.create(SensorService::class.java)
                    valueService.valueTemp().enqueue(object : Callback<List<ValueItem>> {
                        override fun onResponse(call: Call<List<ValueItem>>, response: Response<List<ValueItem>>) {
                            adapterTemp.valueList.addAll(response.body() as List<ValueItem>)
                            adapterTemp.notifyDataSetChanged()
                            valueTemp.setText(adapterTemp.valueList[0].temp)

                            if (adapterTemp.valueList[0].temp.toFloat() >= 22.0 || adapterEmf.valueList[0].temp.toFloat() <= 17.0) {
                                Log.d("value", "기준치 벗어남!!!")
                                tempGetNotificationBuilder()
                            }
                            // 만약에 이거 제대로 찍히면 sensorList.length만큼 루프 돌아서 수치 비교 후,
                            // 여기서 푸쉬알림 추가 or 카카오톡 알림 메시지 전송
                        }

                        override fun onFailure(call: Call<List<ValueItem>>, t: Throwable) {
                            Log.d("valueList", "안들어왔니?!!??")
                        }
                    });

                    valueService.valueHumi().enqueue(object : Callback<List<ValueItem>> {
                        override fun onResponse(call: Call<List<ValueItem>>, response: Response<List<ValueItem>>) {
                            adapterHumi.valueList.addAll(response.body() as List<ValueItem>)
                            adapterHumi.notifyDataSetChanged()
                            Log.d("value 인데긋", adapterHumi.valueList.size.toString())
                            Log.d("안나오니?????", adapterHumi.valueList[0].humi)
                            valueHumi.setText(adapterHumi.valueList[0].humi)
                            if (adapterHumi.valueList[0].humi.toFloat() >= 70 || adapterHumi.valueList[0].humi.toFloat() <= 30) {
                                Log.d("value", "기준치 벗어남!!!")
                                humiGetNotificationBuilder()
                            }
                            // 만약에 이거 제대로 찍히면 sensorList.length만큼 루프 돌아서 수치 비교 후,
                            // 여기서 푸쉬알림 추가 or 카카오톡 알림 메시지 전송
                        }

                        override fun onFailure(call: Call<List<ValueItem>>, t: Throwable) {
                            Log.d("valueList", "안들어왔니?!!??")
                        }
                    });

                    valueService.valueEmf().enqueue(object : Callback<List<ValueItem>> {
                        override fun onResponse(call: Call<List<ValueItem>>, response: Response<List<ValueItem>>) {
                            adapterEmf.valueList.addAll(response.body() as List<ValueItem>)
                            adapterEmf.notifyDataSetChanged()
                            valueEmf.setText(adapterEmf.valueList[0].emf)
                            if (adapterEmf.valueList[0].emf.toFloat() >= 1000) {
                                Log.d("value", "기준치 벗어남!!!")
                                humiGetNotificationBuilder()
                            }
                            // 만약에 이거 제대로 찍히면 sensorList.length만큼 루프 돌아서 수치 비교 후,
                            // 여기서 푸쉬알림 추가 or 카카오톡 알림 메시지 전송
                        }

                        override fun onFailure(call: Call<List<ValueItem>>, t: Throwable) {
                            Log.d("valueList", "안들어왔니?!!??")
                        }
                    });
                    onoff.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.switch_on))
                    temp = 1
                    Toast.makeText(
                        requireContext().applicationContext,
                        "학습 환경 알림이 설정되었습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (temp == 1)
                    onoff.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.switch_off))
            }

//        }



        // Inflate the layout for this fragment
        return view
    }

    companion object {
        fun newInstance(param1: String?, param2: String?): MonitoringFragment {
            return MonitoringFragment()
        }
    }

    // 온도 알림
    private fun tempGetNotificationBuilder() {
        // 알림 채널 생성

        val notificationManager: NotificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "이름"

        //채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(channelId, "app", IMPORTANCE_DEFAULT)
            channel.description = "설명"
            channel.setShowBadge(false)

            notificationManager.createNotificationChannel(channel)
        }

        Log.d("여기는 valueNoti", "들어왔니????")
        val title = "내 학습환경 알리미"
        val content = "학습 기준치에서 온도가 벗어났습니다! :("
        val builder = NotificationCompat.Builder(requireContext(), channelId)

        builder.setSmallIcon(R.drawable.cute)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setAutoCancel(true)
        notificationManager.notify(1, builder.build())
    }

    // 습도 알림
    private fun humiGetNotificationBuilder() {
        // 알림 채널 생성

        val notificationManager: NotificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "이름"

        //채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(channelId, "app", IMPORTANCE_DEFAULT)
            channel.description = "설명"
            channel.setShowBadge(false)

            notificationManager.createNotificationChannel(channel)
        }

        Log.d("여기는 valueNoti", "들어왔니????")
        val title = "내 학습환경 알리미"
        val content = "학습 기준치에서 습도가 벗어났습니다! :("
        val builder = NotificationCompat.Builder(requireContext(), channelId)
        val bigTextStyle = NotificationCompat.BigTextStyle(NotificationCompat.Builder(requireContext()))

        builder.setSmallIcon(R.drawable.cute)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setAutoCancel(true)
        builder.setStyle(bigTextStyle)
        notificationManager.notify(2, builder.build())
    }

    // 이산화탄소 알림
    private fun emfGetNotificationBuilder() {
        // 알림 채널 생성

        val notificationManager: NotificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "이름"

        //채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(channelId, "app", IMPORTANCE_DEFAULT)
            channel.description = "설명"
            channel.setShowBadge(false)

            notificationManager.createNotificationChannel(channel)
        }

        Log.d("여기는 valueNoti", "들어왔니????")
        val title = "내 학습환경 알리미"
        val content = "학습 기준치에서 이산화탄소 농도가 벗어났습니다! :("
        val builder = NotificationCompat.Builder(requireContext(), channelId)

        builder.setSmallIcon(R.drawable.cute)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setAutoCancel(true)

        notificationManager.notify(3, builder.build())
    }

    private fun scorePost() {

    }
}

