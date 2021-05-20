package edu.umb.cs.mbtabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stationList = resources.getStringArray(R.array.rl_alewife_braintree)

        val stationMap: Map<String, String> = resources.getStringArray(R.array.rl_alewife_braintree)
            .zip(resources.getStringArray(R.array.rl_alewife_braintree_id))
            .toMap()

        val adapter = StationAdapter(applicationContext, stationList, stationMap)

        stationsRecyclerView.adapter = adapter
        stationsRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}