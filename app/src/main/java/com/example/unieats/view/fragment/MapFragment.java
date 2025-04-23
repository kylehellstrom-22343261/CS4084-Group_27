package com.example.unieats.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.unieats.R;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.Arrays;

public class MapFragment extends Fragment {

    private final GeoPoint restaurantLocation = new GeoPoint(52.67908912105616, -8.569674306021252); // Example restaurant coordinates (e.g., NYC)
    private MapView mapView;
    private MyLocationNewOverlay myLocationOverlay;
    // The Pavilion: 52.67908912105616, -8.569674306021252

    // Empty Ctor
    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

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

        return view;
    }

    private void drawRoute() {
        myLocationOverlay.runOnFirstFix(() -> {
            GeoPoint currentGeoPoint = myLocationOverlay.getMyLocation();
            Log.d("MapFragment", "User location: " + currentGeoPoint);

            if (currentGeoPoint != null && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    Polyline route = new Polyline();
                    route.setPoints(Arrays.asList(currentGeoPoint, restaurantLocation));

                    route.setPoints(Arrays.asList(currentGeoPoint, restaurantLocation));
                    mapView.getOverlays().add(route);
                    BoundingBox box = BoundingBox.fromGeoPointsSafe(Arrays.asList(currentGeoPoint, restaurantLocation));
                    mapView.zoomToBoundingBox(box, true, 100); // 100 is padding in pixels

                    mapView.invalidate(); // Refresh the map
                });
            } else if (getActivity() != null) {
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getActivity(), "Location not available", Toast.LENGTH_SHORT).show()
                );
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
