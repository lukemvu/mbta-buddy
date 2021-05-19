package edu.umb.cs.mbtabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.stationsRecyclerView
import kotlinx.android.synthetic.main.activity_station_info.*

class StationInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_info)

        //TODO
        // 1. Get station name passed in from intent
        // 2. make api request for predictions and routes
        // 3. build list of predictions
        // 4. populate recyclerview

        val test_list = listOf<String>("First", "Second", "Third", "More Stuff", "Second",
                "Third", "More Stuff", "Second", "Third", "More Stuff").toTypedArray()

//        val station_list = resources.getStringArray(R.array.rl_alewife_braintree)
//
        val adapter = StationInfoAdapter(applicationContext, test_list)
        stationRecyclerView.adapter = adapter
        stationRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}