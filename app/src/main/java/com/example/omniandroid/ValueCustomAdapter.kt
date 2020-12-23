package com.example.omniandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omniandroid.R
import com.example.omniandroid.Sensor
import com.example.omniandroid.SensorItem
import kotlinx.android.synthetic.main.value_item_recycler.view.*

class ValueCustomAdapter : RecyclerView.Adapter<valueHolder>() {
    var valueList = mutableListOf<ValueItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): valueHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.value_item_recycler, parent, false)
        return valueHolder(view)
    }

    override fun onBindViewHolder(holder: valueHolder, position: Int) {
        val value = valueList.get(position)
        holder.setValue(value)
    }

    override fun getItemCount(): Int {
        return valueList.size
    }
}

class valueHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setValue(value: ValueItem){
        if(value.sort == "1")
            itemView.textValue.text = value.humi
        else if(value.sort == "2")
            itemView.textValue.text = value.temp
        else if(value.sort == "3")
            itemView.textValue.text = value.emf
    }
}