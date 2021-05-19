package edu.umb.cs.mbtabuddy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_station_info.view.*

class StationInfoAdapter(
        var mContext: Context,
        var station: Array<String>
) : RecyclerView.Adapter<StationInfoAdapter.StationInfoViewHolder>() {

    inner class StationInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            var stationInfoName: TextView = itemView.findViewById(R.id.stationInfoName)
            var stationInfoImg: ImageView = itemView.findViewById(R.id.stationInfoImg)
            var secondaryLayout: ConstraintLayout = itemView.findViewById(R.id.secondaryLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station_info, parent, false)
        return StationInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return station.size
    }

    override fun onBindViewHolder(holder: StationInfoViewHolder, position: Int) {
        holder.itemView.apply {
            stationInfoName.text = station[position]
            stationInfoImg.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
}