import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omniandroid.R
import com.example.omniandroid.Sensor
import com.example.omniandroid.SensorItem
import kotlinx.android.synthetic.main.item_recycler.view.*

class CustomAdapter : RecyclerView.Adapter<Holder>() {
    var sensorList = mutableListOf<SensorItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val sensor = sensorList.get(position)
        holder.setSensor(sensor)
    }

    override fun getItemCount(): Int {

        return sensorList.size
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setSensor(sensor: SensorItem){
        itemView.textId.text = sensor.id
        itemView.textUserId.text = sensor.userId
        itemView.textCreatedAt.text = sensor.createdAt
        itemView.textSort.text = sensor.sort

        if(sensor.sort == "1")
            itemView.textValue.text = sensor.humi
        else if(sensor.sort == "2")
            itemView.textValue.text = sensor.temp
        else if(sensor.sort == "3")
            itemView.textValue.text = sensor.emf
    }
}