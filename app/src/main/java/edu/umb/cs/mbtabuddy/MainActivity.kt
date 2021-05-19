package edu.umb.cs.mbtabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO
        // 1.

        val station_list = resources.getStringArray(R.array.rl_alewife_braintree)

        val adapter = StationAdapter(applicationContext, station_list)

        stationsRecyclerView.adapter = adapter
        stationsRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}