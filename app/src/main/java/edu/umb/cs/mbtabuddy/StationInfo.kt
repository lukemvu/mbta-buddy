package edu.umb.cs.mbtabuddy

import android.gesture.Prediction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationAttributes
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.stationsRecyclerView
import kotlinx.android.synthetic.main.activity_station_info.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class StationInfo : AppCompatActivity() {

    companion object {
        var routesData: Routes? = null
        var predictionsData: Predictions? = null
        val stationIcon: MutableMap<String, Int> = mutableMapOf()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_info)

        // Get station name & station id from previous activity
        val stationName: String = intent.getStringExtra("stationName").toString()
        val stationId: String = intent.getStringExtra("stationId").toString()

        val stationNameText = findViewById<TextView>(R.id.stationInfoNameTitle)
        stationNameText.text = stationName

//        val stationIcon: MutableMap<String, Int> = mutableMapOf()
        stationIcon["Red"] = R.drawable.rl_icon
        stationIcon["Orange"] = R.drawable.ol_icon
        stationIcon["Mattapan"] = R.drawable.m_icon
        stationIcon["Green-B"] = R.drawable.b_icon
        stationIcon["Green-C"] = R.drawable.c_icon
        stationIcon["Green-D"] = R.drawable.d_icon
        stationIcon["Green-E"] = R.drawable.e_icon

        fetchRoutes(stationId)
    }

    override fun onRestart() {
        super.onRestart()
        routesData = null
        predictionsData = null
    }

    fun fetchRoutes(stationId: String) {
        val url = "https://api-v3.mbta.com/routes?filter[type]=0,1&filter[stop]=$stationId"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                val gson = GsonBuilder().create()
                routesData = gson.fromJson(body, Routes::class.java)
                fetchPredictions(stationId)
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute routes request")
            }
        })
    }

    fun fetchPredictions(stationId: String) {
        val url = "https://api-v3.mbta.com/predictions?filter[stop]=$stationId&filter[route_type]=0,1"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                val gson = GsonBuilder().create()
                predictionsData = gson.fromJson(body, Predictions::class.java)

                val plist = buildPredictionList(buildRoutesMap())

                runOnUiThread {
                    val adapter = StationInfoAdapter(applicationContext, plist)
                    stationRecyclerView.adapter = adapter
                    stationRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute predictions request")
            }
        })
    }

    fun getETA(predictedTime: String) : String {
        val time = OffsetDateTime.parse(predictedTime)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return formatter.format(time)
    }

    fun buildRoutesMap() : MutableMap<String, StationRoute> {
        var routesMap: MutableMap<String, StationRoute> = mutableMapOf()
        for(route in routesData!!.data) {
            routesMap.putIfAbsent(
                    route.id,
                    StationRoute(
                            route.id,
                            route.attributes.direction_names,
                            route.attributes.direction_destinations
                    )
            )
        }
        return routesMap
    }

    fun buildPredictionList(routesMap: MutableMap<String, StationRoute>) : MutableList<StopPrediction> {
        var predictionList: MutableList<StopPrediction> = mutableListOf()
        for(prediction in predictionsData!!.data) {
            val directionId = prediction.attributes.direction_id
            val routeId = prediction.relationships.route.data.id
            val eta = getETA(prediction.attributes.arrival_time)
            //TODO ADD NULL CHECK
            predictionList.add(StopPrediction(
                    routesMap[routeId]!!.direction_name[directionId],
                    routesMap[routeId]!!.direction_destination[directionId],
                    eta,
                    stationIcon[routeId]!!
            ))
        }
        return predictionList
    }


}

class Routes(val data: List<Route>)
class Route(val attributes: RouteAttribute, val id: String)
class RouteAttribute(val direction_destinations: List<String>, val direction_names: List<String>)

class Predictions(val data: List<edu.umb.cs.mbtabuddy.Prediction>)
class Prediction(val attributes: PredictionAttribute, val relationships: PredictionRelationships)
class PredictionAttribute(val arrival_time: String, val departure_time: String, val direction_id: Int)
class PredictionRelationships(val route: PredictionRoute)
class PredictionRoute(val data: PredictionData)
class PredictionData(val id: String)

