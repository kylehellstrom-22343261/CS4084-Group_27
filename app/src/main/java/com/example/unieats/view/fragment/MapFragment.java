package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.unieats.BuildConfig;
import com.example.unieats.R;
import com.example.unieats.controller.RestaurantController;
import com.example.unieats.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private String restaurantName;
    private double aLatitiute;
    private double aLongitude;
    private GeoPoint restaurantLocation;
    private MapView mapView;
    private TextView routeDistanceTextView;
    public double distanceKm;
    private MyLocationNewOverlay myLocationOverlay;
    // The Pavilion: 52.67908912105616, -8.569674306021252

    // Empty Ctor
    public MapFragment(String restaurantName) {
        this.restaurantName = restaurantName;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        routeDistanceTextView = view.findViewById(R.id.route_distance);
        TextView restaurantNameTextView = view.findViewById(R.id.restaurant_name);
        restaurantNameTextView.setText(restaurantName);


        // Get latitude and longitude
        RestaurantController.getRestaurants(restaurants -> {
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getBusinessName().equals(restaurantName)) {
                    aLatitiute = restaurant.getLatitude();
                    aLongitude = restaurant.getLongitude();
                    break;
                }
            }
                if(aLatitiute == 0 && aLongitude == 0){
                    aLatitiute = restaurants.get(0).getLatitude();
                    aLongitude = restaurants.get(0).getLongitude();
                }
            restaurantLocation = new GeoPoint(aLatitiute, aLongitude);
            setUpMap(view);
        });

        return view;
    }
    private void setUpMap(View view) {
        mapView = view.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        // Initialize location overlay
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableFollowLocation();


        mapView.getOverlays().add(myLocationOverlay);
        mapView.getController().setZoom(15);
        mapView.getController().setCenter(restaurantLocation);

        // Set up the restaurant location marker
        Marker restaurantMarker = new Marker(mapView);
        restaurantMarker.setTitle("The Pavilion"); //TODO: Change Automatically
        restaurantMarker.setPosition(restaurantLocation);
//        restaurantMarker.setIcon(getResources().getDrawable(R.drawable.restaurant_marker)); // Add marker image
        mapView.getOverlays().add(restaurantMarker);

        // Draw a line between current location and restaurant
        drawRoute();
    }

    private void drawRoute() {
        myLocationOverlay.runOnFirstFix(() -> {
            GeoPoint start = myLocationOverlay.getMyLocation();

            if (start != null && getActivity() != null) {
                new Thread(() -> {
                    HttpURLConnection conn = null;
                    try {
                        String apiKey = BuildConfig.ORS_API_KEY;
                        String urlStr = "https://api.openrouteservice.org/v2/directions/foot-walking/geojson";
                        URL url = new URL(urlStr);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Authorization", apiKey);
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setDoOutput(true);

                        // Construct the JSON body
                        JSONObject jsonBody = new JSONObject();
                        JSONArray coordinates = new JSONArray();
                        coordinates.put(new JSONArray().put(start.getLongitude()).put(start.getLatitude()));
                        coordinates.put(new JSONArray().put(restaurantLocation.getLongitude()).put(restaurantLocation.getLatitude()));
                        jsonBody.put("coordinates", coordinates);

                        String bodyStr = jsonBody.toString();
                        conn.getOutputStream().write(bodyStr.getBytes("UTF-8"));

                        int responseCode = conn.getResponseCode();
                        InputStream inputStream = (responseCode >= 200 && responseCode < 300)
                                ? conn.getInputStream()
                                : conn.getErrorStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }

                        Log.d("ORS", "Response: " + sb.toString());

                        JSONObject response = new JSONObject(sb.toString());
                        JSONArray coordinatesArray = response
                                .getJSONArray("features")
                                .getJSONObject(0)
                                .getJSONObject("geometry")
                                .getJSONArray("coordinates");

                        // Extract total distance in meters
                        double distanceInMeters = response
                                .getJSONArray("features")
                                .getJSONObject(0)
                                .getJSONObject("properties")
                                .getJSONObject("summary")
                                .getDouble("distance");

                        // Convert to kilometers
                        distanceKm = distanceInMeters / 1000.0;
                        getActivity().runOnUiThread(() -> {
                            routeDistanceTextView.setText(String.format("%.3f", distanceKm));
                        });

                        List<GeoPoint> geoPoints = new ArrayList<>();
                        for (int i = 0; i < coordinatesArray.length(); i++) {
                            JSONArray coord = coordinatesArray.getJSONArray(i);
                            double lon = coord.getDouble(0);
                            double lat = coord.getDouble(1);
                            geoPoints.add(new GeoPoint(lat, lon));
                        }

                        getActivity().runOnUiThread(() -> {
                            Polyline route = new Polyline();
                            route.setPoints(geoPoints);
                            route.setColor(getResources().getColor(R.color.green));
                            route.setWidth(6f);
                            mapView.getOverlays().add(route);
                            mapView.zoomToBoundingBox(BoundingBox.fromGeoPointsSafe(geoPoints), true, 100);
                            mapView.invalidate();
                        });

                    } catch (Exception e) {
                        Log.e("ORS", "Route draw failed", e);
                    } finally {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }
                }).start();
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume(); // Resume the map view
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause(); // Pause the map view
        }
    }
}
