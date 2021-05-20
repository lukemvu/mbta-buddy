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
        var stationList: Array<String>,
         var stationMap: Map<String, String>
        ) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    inner class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, StationInfo::class.java)
                intent.putExtra("stationName", stationList[layoutPosition])
                intent.putExtra("stationId", stationMap[stationList[layoutPosition]])
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
            val imgSrc = if (position==0) {
                R.drawable.ic_rl_stop_top
            } else if (position == stationList.size-1) {
                R.drawable.ic_rl_stop_bottom
            } else {
                R.drawable.ic_red_station_middle
            }
            stationImg.setImageResource(imgSrc)
        }
    }


}