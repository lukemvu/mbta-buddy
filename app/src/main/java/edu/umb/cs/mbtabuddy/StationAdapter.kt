package edu.umb.cs.mbtabuddy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_station.view.*

class StationAdapter(
        var stationList: Array<String>,
        var stationMap: Map<String, String>,
        val stationColor: List<Int>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_station, parent, false)
        return StationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.itemView.apply {
            stationName.text = stationList[position]
            val imgSrc = when (position) {
                0 -> {
                    stationColor[0]
                }
                stationList.size-1 -> {
                    stationColor[2]
                }
                else -> {
                    stationColor[1]
                }
            }
            stationImg.setImageResource(imgSrc)
        }
    }


}