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
import kotlinx.android.synthetic.main.item_station.view.*

class StationAdapter(
        var mContext: Context,
        var stationList: Array<String>
        ) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    inner class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            var stationName: TextView = itemView.findViewById(R.id.stationName)
            var stationImg: ImageView = itemView.findViewById(R.id.stationImg)
            var mainLayout: ConstraintLayout = itemView.findViewById(R.id.mainLayout)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, StationInfo::class.java)
                println(layoutPosition)
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.itemView.apply {
            stationName.text = stationList[position]
            stationImg.setImageResource(R.drawable.ic_red_station_middle)
        }
    }


}