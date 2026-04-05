package com.applicationslab.ayurvedictreatment.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.applicationslab.ayurvedictreatment.R;
import com.applicationslab.ayurvedictreatment.utility.PermissionHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.*;
import com.google.android.libraries.places.api.*;
import com.google.android.libraries.places.api.model.*;
import com.google.android.libraries.places.api.net.*;

import org.json.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_PERMISSION = 100;

    String PLACES_API_KEY = "YOUR_PLACES_API_KEY";

    PlacesClient placesClient;
    PermissionHandler permission;

    ProgressBar progressBar;
    GoogleMap googleMap;

    double myLat = 0, myLon = 0;
    String myLocName = "";

    ArrayList<String> allNames;
    ArrayList<Double> allLats;
    ArrayList<Double> allLons;

    boolean markersAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        permission = new PermissionHandler(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
        } else {
            initView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ayurvedic Hospitals");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progressBar);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        initPlacesApi();
    }

    private void initPlacesApi() {

        if (!Places.isInitialized()) {
            Places.initialize(this, PLACES_API_KEY);
        }

        placesClient = Places.createClient(this);

        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);

        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(fields);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Task<FindCurrentPlaceResponse> response = placesClient.findCurrentPlace(request);

        response.addOnSuccessListener(placeResponse -> {

            Place place = placeResponse.getPlaceLikelihoods().get(0).getPlace();

            myLocName = place.getName();
            LatLng latLng = place.getLatLng();

            myLat = latLng.latitude;
            myLon = latLng.longitude;

            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                    + myLat + "," + myLon
                    + "&radius=5000&type=hospital&key=" + PLACES_API_KEY;

            new PlaceFinder().execute(url);
        });

        response.addOnFailureListener(e ->
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        if (allLats != null) {
            addMarkers();
        }
    }

    private void addMarkers() {

        if (markersAdded) return;

        markersAdded = true;

        for (int i = 0; i < allLats.size(); i++) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(allLats.get(i), allLons.get(i)))
                    .title("Hospital")
                    .snippet(allNames.get(i)));
        }

        LatLng myPos = new LatLng(myLat, myLon);

        googleMap.addMarker(new MarkerOptions()
                .position(myPos)
                .title("You are here"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 14));
    }

    class PlaceFinder extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                return sb.toString();

            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject json = new JSONObject(result);
                JSONArray array = json.getJSONArray("results");

                allNames = new ArrayList<>();
                allLats = new ArrayList<>();
                allLons = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {

                    JSONObject obj = array.getJSONObject(i);

                    String name = obj.getString("name");

                    JSONObject loc = obj.getJSONObject("geometry")
                            .getJSONObject("location");

                    double lat = loc.getDouble("lat");
                    double lon = loc.getDouble("lng");

                    allNames.add(name);
                    allLats.add(lat);
                    allLons.add(lon);
                }

                progressBar.setVisibility(View.GONE);

                if (googleMap != null) {
                    addMarkers();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}