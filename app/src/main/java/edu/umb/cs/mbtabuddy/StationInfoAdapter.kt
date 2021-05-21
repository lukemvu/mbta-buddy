package edu.umb.cs.mbtabuddy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_prediction.view.*

class StationInfoAdapter(
        var mContext: Context,
        var predictionList: List<StopPrediction>
) : RecyclerView.Adapter<StationInfoAdapter.StationInfoViewHolder>() {

    inner class StationInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_prediction, parent, false)
        return StationInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return predictionList.size
    }

    override fun onBindViewHolder(holder: StationInfoViewHolder, position: Int) {
        holder.itemView.apply {
            stationInfoName.text = predictionList[position].routeDestination
            stationInfoDirection.text = predictionList[position].routeDirection
            stationInfoImg.setImageResource(predictionList[position].imgSrc)
            arrival_time.text = predictionList[position].ETA
        }
    }
}