# mbta-buddy

## Use Instructions
Users should be able to simply clone this git repo and import it into AndroidStudio to emulate/install onto their Android device.

<img src="https://github.com/lukemvu/mbta-buddy/blob/main/img/preview1.png" width=450 align=center>
<img src="https://github.com/lukemvu/mbta-buddy/blob/main/img/preview4.png" width=450 align=center>
<!-- ![Application Screenshots](https://github.com/lukemvu/mbta-buddy/blob/main/img/preview2.png)
![Application Screenshots](https://github.com/lukemvu/mbta-buddy/blob/main/img/preview3.png)
![Application Screenshots](https://github.com/lukemvu/mbta-buddy/blob/main/img/preview4.png) -->

## Project Statement
MBTA Buddy is an application that provides users with up to date train
arrival/departure predictions at stops along MBTA lines. The application allows users to navigate between different lines, e.g. Redline-Braintree, Greenline-B, with an easy horizontal swipe gesture. Users can navigate up and down the line with vertical scrolling. Clicking on a stop will list the upcoming arrival/departure times for trains and their direction of travel at that particular stop.

The purpose of this project was to provide users with a fast, intuitive, light weight application for getting train scheduling. Google Maps does similar and the Android app store hosts a number of MBTA tracking type applications but all are bloated with features and take a significant number of steps to get the information you need.

This app is not intended to replace the use of those applications. MBTA Buddy is not a route planner; Google Maps would be a better alternative for planning out a trip. MBTA Buddy does fulfill a role in getting quick arrival/departure predictions.

## Application Design

This application is targeted towards mobile phone devices but will work on other devices. It requires network services as the app makes HTTP requests for prediction data from the MBTA API.

### Overview
- MBTA Buddy features two activities: MainActivity and StationInfo. MainActivity displays at the top a tab layout of the different MBTA lines and a list of stops on that particular line. Horizontal swipes allow for navigation in between these different views. Vertical swipes allow navigation between stops.
- On tapping a particular stop, StationInfo is started and displays a list of predicted arrival/departure times for trains and their direction of travel. An intent with the stopID and stopName is passed to this StationInfo activity. To navigate back into the MainActivity and view of the lines/stops, the user can either press the back button on their device or the back arrow at the top of the application.
- Lists of the station names and station ids are stored in the strings.xml file of resources in the package structure. Each line has a list of station names and station ids. Drawables contains a mix of .svg vectors and .png bitmap images to display the graphics for the lines and stops.
- MainActivity creates an instance of a ViewPageAdapter and adds each line's fragment to itself. Each fragment pulls from the string resources and creates a mapping of the station name to id as well as list of each stop's image id to attach. StationAdapter, extended from the RecyclerView Adapter populates the fragment with a RecyclerView of the list of stations.
- StationViewHolder of StationAdapter contains an onClickListener; when a station is clicked, an intent is created with the stationName and stationID added and starts the StationInfo activity.
- StationInfo first builds a mapping of line ids and their respective image source. A series of functions are called from each other starting with fetchRoutes(). fetchRoutes() makes an aynchronous HTTP GET request to the MBTA API to build a list of routes offered at the specified station. GSON is used to parse the JSON data into a data object. festingPredictions() is called and makes a request for prediction data  at the specified stop. Here, buildRoutesMap() is called on the routeData object to build a mapping of the route id to the routes direction destination. This is passed along with the predictionsData to buildPredictionList() which creates the final list of StopPrediction objects that contain the direction, destination, estimated time of arrival, and image source for the stop prediction. the recyclerView is updated with this list and the view is created.

### Technologies Used
- ViewPager - This is layout manager that allows users to flip left and right through pages of data. Here I use it to navigate between different fragments containing the layout of the MBTA lines.
- Fragment - Each MBTA line has a fragment class that provides the data to populate its view
- RecyclerView - This view allows for dynamically displaying the stops on each MBTA line, every fragment contains a recyclerView.
- OkHTTPClient - This library is used to make asynchronous HTTP GET requests to the MBTA API to retrieve JSON formatted prediction and route data
- GSON - This library is used to parse the JSON formatted data into data objects to retrieve the target data.

## Application Implementation and Evaluation

The application was built on and tested for Android API 28. Prediction data will be available as long as the app is used during times when trains are running. During initial testing, the app was crashing on certain stops, namely stations where routes started and ended. This was due to nulls in the arrival field of the prediction data. Trains that originate from these stations do not have a arrival time because they start there. I fixed this by adding a null check and choosing to use the departure time as an ETA instead. Some stations come up with no predictions; this is due to the station not currently being serviced by a train line for construction or some reason. The application does not crash here but simply delivers an empty list of predictions.
