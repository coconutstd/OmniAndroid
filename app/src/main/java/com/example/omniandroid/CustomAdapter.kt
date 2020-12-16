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
        itemView.textPositionId.text = sensor.positionId
        itemView.textId.text = sensor.id
        if(sensor.id == "user_temp")
            itemView.textPayload.text = sensor.payload.temp
        else if(sensor.id == "user_humi")
            itemView.textPayload.text = sensor.payload.humi
        else if(sensor.id =="user_emf")
            itemView.textPayload.text = sensor.payload.emf
    }
}