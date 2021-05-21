package edu.umb.cs.mbtabuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_predictions.*
import okhttp3.*
import java.io.IOException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class StationInfo : AppCompatActivity() {

    companion object {
        const val API_KEY: String = "dbb62ac4bb9144268d0fa66a1a147a93"
        val stationIcon: MutableMap<String, Int> = mutableMapOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predictions)

        val stationName: String = intent.getStringExtra("stationName").toString()
        val stationId: String = intent.getStringExtra("stationId").toString()

        val stationNameText = findViewById<TextView>(R.id.stationInfoNameTitle)
        stationNameText.text = stationName

        stationIcon["Red"] = R.drawable.rl_icon
        stationIcon["Orange"] = R.drawable.ol_icon
        stationIcon["Mattapan"] = R.drawable.m_icon
        stationIcon["Green-B"] = R.drawable.b_icon
        stationIcon["Green-C"] = R.drawable.c_icon
        stationIcon["Green-D"] = R.drawable.d_icon
        stationIcon["Green-E"] = R.drawable.e_icon

        fetchRoutes(stationId)
    }

    fun fetchRoutes(stationId: String) {
        val url = "https://api-v3.mbta.com/routes?filter[type]=0,1&filter[stop]=$stationId&api_key=$API_KEY"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                val gson = GsonBuilder().create()
                val routesData = gson.fromJson(body, Routes::class.java)
                fetchPredictions(stationId, routesData)
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute routes request")
            }
        })
    }

    fun fetchPredictions(stationId: String, routesData: Routes) {
        val url = "https://api-v3.mbta.com/predictions?filter[stop]=$stationId&filter[route_type]=0,1&api_key=$API_KEY"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val predictionsData = gson.fromJson(body, Predictions::class.java)

                val plist = buildPredictionList(predictionsData, buildRoutesMap(routesData))

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

    fun buildRoutesMap(routesData: Routes) : MutableMap<String, StationRoute> {
        var routesMap: MutableMap<String, StationRoute> = mutableMapOf()
        for(route in routesData.data) {
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

    fun buildPredictionList(
        predictionsData: Predictions,
        routesMap: MutableMap<String, StationRoute>
    ) : MutableList<StopPrediction> {
        var predictionList: MutableList<StopPrediction> = mutableListOf()
        for(prediction in predictionsData!!.data) {
            val directionId = prediction.attributes.direction_id
            val routeId = prediction.relationships.route.data.id

            // arrival times are null where routes originate form
            var time = prediction.attributes.arrival_time

            // use departure time where arrival time is null
            if (time==null) time = prediction.attributes.departure_time

            // skip predictions where there is no estiamted time
            if (time==null) continue
            val eta = getETA(time)
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

