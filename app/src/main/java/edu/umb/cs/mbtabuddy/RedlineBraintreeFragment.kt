package edu.umb.cs.mbtabuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_line.view.*

class RedlineBraintreeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View =  inflater.inflate(R.layout.fragment_line, container, false)

        val stationList = resources.getStringArray(R.array.rl_alewife_braintree)
        val stationMap: Map<String, String> = resources.getStringArray(R.array.rl_alewife_braintree)
            .zip(resources.getStringArray(R.array.rl_alewife_braintree_id))
            .toMap()
        val stationColor: List<Int> = listOf(
            R.drawable.ic_rl_stop_top,
            R.drawable.ic_rl_stop_middle,
            R.drawable.ic_rl_stop_bottom
        )

        view.findViewById<RecyclerView>(R.id.stationsRecyclerView)

        val adapter = StationAdapter(stationList, stationMap, stationColor)

        view.stationsRecyclerView.adapter = adapter
        view.stationsRecyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }
}