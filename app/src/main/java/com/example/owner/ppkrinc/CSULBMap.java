package com.example.owner.ppkrinc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;


import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CSULBMap extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private static final int overview = 0;
    private GoogleMap mMap;
    private DrawerLayout drawerLayout;
    private Fragment mDrawer;
    Bundle loginData;

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.directionsApiKey))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_csulbmap);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loginData =  getIntent().getExtras();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        FusedLocationProviderClient currentLocationRequest = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> currentLocation = currentLocationRequest.getLastLocation();
        currentLocation.addOnCompleteListener(this, (task)-> {
            if (task.isSuccessful()){
                Location location = task.getResult();
                if (location != null) {
                    getRouteToCSULB(new com.google.maps.model.LatLng(location.getLatitude(), location.getLongitude()));
                }
            }
        });
    }

    private void getRouteToCSULB(com.google.maps.model.LatLng latLng) {
        DateTime now = new DateTime();

        try {
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING)
                    .origin(latLng)
                    .destination(new com.google.maps.model.LatLng(33.786050, -118.109669))
                    .departureTime(now)
                    .await();
            addMarkersToMap(result, mMap);
            positionCamera(result.routes[0], mMap);
            addPolyline(result, mMap);

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Add a marker in CSULB and move the camera
//        com.google.android.gms.maps.model.LatLng paloVerdeSouth = new com.google.android.gms.maps.model.LatLng(33.786050, -118.109669);
//        mMap.addMarker(new MarkerOptions().position(paloVerdeSouth).title("Palo Verde South Parking Structure"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paloVerdeSouth, 16));
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].startLocation.lat,results.routes[0].legs[0].startLocation.lng)).title(results.routes[0].legs[0].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat,results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].endAddress).snippet(getEndLocationTitle(results))).showInfoWindow();
    }
    private String getEndLocationTitle(DirectionsResult results){
        return   "Distance :" + results.routes[overview].legs[overview].distance.humanReadable + ", Time :"+ results.routes[overview].legs[overview].duration.humanReadable + ", Wait Time: 5 mins" ;
    }
    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }
    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].endLocation.lat, route.legs[overview].endLocation.lng), 16));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = null;
        switch (menuItem.getItemId()){
            case R.id.postParkingMenuItem:
                intent = new Intent(getApplicationContext(), PostParkingSpotActivity.class);
                break;
            case R.id.searchParkingMenuItem:
                intent = new Intent(getApplicationContext(), SearchParking.class);
                break;
            case R.id.aboutUsMenuItem:
                intent = new Intent(getApplicationContext(), AboutUs.class);
                break;
            case R.id.recentMatchesMenuItem:
                intent = new Intent(getApplicationContext(), RecentMatches.class);
                break;
            case R.id.myAccountMenuItem:
                intent = new Intent(getApplicationContext(), MyAccount.class);
                intent.putExtras(loginData);
                break;
            case R.id.logOutMenuItem:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();
                break;

            case R.id.chatItem:
                intent = new Intent(getApplicationContext(), Users.class);
                break;

        }
        if (intent != null) {
            startActivity(intent);
            return true;
        }
        else
            return false;
    }
}
