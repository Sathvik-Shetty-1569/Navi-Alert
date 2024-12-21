package com.example.miniprojectsem5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.miniprojectsem5.databinding.ActivityMapsBinding;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private PlacesClient placesClient;
    private List<Place> policeStations = new ArrayList<>();
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Button btnShowLocations;
    private String[] locationNames = {"Kharghar Police Station","Kalamboli Police Station","Panvel City Police Station","Panvel Taluka Police Station", "Vashi Police Station","Colaba Police Station","Rasayni Police Station", "Marine Drive Police Station", "Dadar Police Station", "Bandra Police Station", "Andheri Police Station", "Powai Police Station","Borivali Police Station","Mulund Police Station","Pen Police Station"};
    private LatLng[] locations = {
            new LatLng(19.0462,73.0699),
            new LatLng(19.0176,73.1061),
            new LatLng(18.9894,73.1175),
            new LatLng(18.9325,73.1215),
            new LatLng(19.0771,72.9982),
            new LatLng(18.9189, 72.8236),
            new LatLng(18.885791,73.172970),
            new LatLng(18.9450, 72.8238),
            new LatLng(19.0191, 72.8423),
            new LatLng(19.0544, 72.8400),
            new LatLng(19.1197, 72.8464),
            new LatLng(19.1234, 72.9045),
            new LatLng(19.2357, 72.8543),
            new LatLng(19.1724, 72.9472),
            new LatLng(18.7390, 73.0957)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Places.initialize(getApplicationContext(), "AIzaSyAUDX2GMv7MX6Mi1D8tBbsDvlu1OyuaDOY");
        placesClient = Places.createClient(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        btnShowLocations = findViewById(R.id.place_picker);
        btnShowLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterNearbyPoliceStations();
            }
        });
    }

    private void moveCameraToCurrentLocation() {
        if (currentLocation != null) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
        } else {
            Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
        }
    }



    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            if (currentLocation != null) {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }




    private double calculateDistance(LatLng latLng1, LatLng latLng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(latLng2.latitude - latLng1.latitude);
        double dLng = Math.toRadians(latLng2.longitude - latLng1.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latLng1.latitude)) * Math.cos(Math.toRadians(latLng2.latitude)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private void filterNearbyPoliceStations() {
        LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        List<String> nearbyPoliceStations = new ArrayList<>();
        List<LatLng> nearbyLocations = new ArrayList<>();

        for (int i = 0; i < locations.length; i++) {
            double distance = calculateDistance(userLocation, locations[i]);
            if (distance <= 5.0) {
                nearbyPoliceStations.add(locationNames[i]);
                nearbyLocations.add(locations[i]);
            }
        }

        if (!nearbyPoliceStations.isEmpty()) {
            showNearbyPoliceStations(nearbyPoliceStations, nearbyLocations);
        } else {
            Toast.makeText(this, "No police stations within 5 km.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNearbyPoliceStations(List<String> nearbyPoliceStations, List<LatLng> nearbyLocations) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Select a Nearby Police Station");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nearbyPoliceStations);

        builder.setAdapter(adapter, (dialog, which) -> {
            LatLng selectedLocation = nearbyLocations.get(which);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 12));
            mMap.addMarker(new MarkerOptions().position(selectedLocation).title(nearbyPoliceStations.get(which)));
            Toast.makeText(MapsActivity.this, "Selected: " + nearbyPoliceStations.get(which), Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
            } else {
                Toast.makeText(this, "Location Permission is denied. Please allow the permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
