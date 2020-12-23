package com.example.omniandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omniandroid.R
import com.example.omniandroid.Sensor
import com.example.omniandroid.SensorItem
import kotlinx.android.synthetic.main.item_recycler.view.*
import kotlinx.android.synthetic.main.score_item_recycler.view.*

class ScoreCustomAdapter : RecyclerView.Adapter<scoreHolder>() {
    var scoreList = mutableListOf<ScoreItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): scoreHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_item_recycler, parent, false)
        return scoreHolder(view)
    }

    override fun onBindViewHolder(holder: scoreHolder, position: Int) {
        val score = scoreList.get(position)
        holder.setScore(score)
    }

    override fun getItemCount(): Int {
        return scoreList.size
    }
}

class scoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setScore(score: ScoreItem){
        itemView.textApplied_score.text = score.applied_score.toString()
        itemView.textScore_per_second.text = score.score_per_second.toString()
        itemView.textScoreId.text = score.id
        itemView.textScoreCreatedAt.text = score.createdAt
        itemView.textMeeting_title.text = score.meeting_title
        itemView.textScoreUserId.text = score.userId
        itemView.textTotal_time.text = score.total_time
    }
}