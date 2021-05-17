package edu.umb.cs.mbtabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var colorList = listOf<String>("Red")

        var stationList = mutableListOf<Station>(
            Station("Alewife", colorList, 0, 0),
            Station("Davis", colorList, 0, 0),
            Station("Porter", colorList, 0, 0),
            Station("Harvard", colorList, 0, 0),
            Station("Central", colorList, 0, 0),
            Station("Kendall/MIT", colorList, 0, 0),
            Station("Charles", colorList, 0, 0),
            Station("Park Street", colorList, 0, 0),
            Station("Downtown", colorList, 0, 0),
            Station("South Station", colorList, 0, 0),
            Station("Broadway", colorList, 0, 0),
            Station("Andrew", colorList, 0, 0),
            Station("JFK/UMASS", colorList, 0, 0),
            Station("North Quincy", colorList, 0, 0),
            Station("Wollaston", colorList, 0, 0),
            Station("Quincy Center", colorList, 0, 0),
            Station("Quincy Adams", colorList, 0, 0),
            Station("Braintree", colorList, 0, 0)
        )

        val adapter = StationAdapter(stationList)
        stationsRecyclerView.adapter = adapter
        stationsRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}